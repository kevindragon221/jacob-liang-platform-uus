/**
 *功能说明：
 * @author jacobliang
 * @time @Aug 26, 2010 @3:47:43 PM
 */
package com.bhtec.common.qq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cometd.Bayeux;
import org.cometd.Channel;
import org.cometd.Client;
import org.cometd.RemoveListener;
import org.mortbay.cometd.BayeuxService;
import org.mortbay.log.Log;

public class QQService extends BayeuxService {
	/**
	 * A map(channel, map(userName, clientId))
	 */
	private final ConcurrentMap<String, Map<String, String>> _members = new ConcurrentHashMap<String, Map<String, String>>();

	public QQService(Bayeux bayeux) {
		super(bayeux, "public");
		subscribe("/public/**", "trackMembers");
		subscribe("/private/chat", "privateChat");

		System.out.println("**********************************************");
		System.out.println("QQService");
		System.out.println("**********************************************");
	}

	public void trackMembers(final Client joiner, final String channelName,Map<String, Object> data, final String messageId) {

		if (Boolean.TRUE.equals(data.get("join"))) {

			Log.info("add members : " + joiner);

			Map<String, String> membersMap = _members.get(channelName);

			if ("rejoin".equals(data.get("action"))) {
				Log.info("rejoin");
				joiner.deliver(getClient(), channelName, membersMap.keySet(),messageId);
				return;
			}
			Log.info("region next");
			if (membersMap == null) {
				Log.info("membersMap == null ");

				Map<String, String> newMembersMap = new ConcurrentHashMap<String, String>();
				membersMap = _members.putIfAbsent(channelName, newMembersMap);
				if (membersMap == null) {
					membersMap = newMembersMap;
				}
			}

			final Map<String, String> members = membersMap;
			final String userName = (String) data.get("user");
			// 踢人
			// if(members.containsKey(userName)){
			// Log.info("This user has joined!");
			// return;
			// }

			members.put(userName, joiner.getId());

			joiner.addListener(new RemoveListener() {
				public void removed(String clientId, boolean timeout) {
					
					Log.info("remove members: " + members);
					members.values().remove(clientId);
					// Broadcast the members to all existing members
					Channel channel = getBayeux().getChannel(channelName, false);
					if (channel != null) {
						channel.publish(getClient(), members.keySet(),messageId);
					}
				}
			});
			// 现有人员
			Log.info("Members: " + members);

			// Broadcast the members to all existing members
			getBayeux().getChannel(channelName, false).publish(getClient(),members.keySet(), messageId);
		}
	}

	public void privateChat(Client source, String channel,Map<String, Object> data, String messageId) {
		String roomName = (String) data.get("room");
		Map<String, String> membersMap = _members.get(roomName);
		String peerName = (String) data.get("peer");
		String peerId = membersMap.get(peerName);
		if (peerId != null) {
			Client peer = getBayeux().getClient(peerId);
			if (peer != null) {
				Map<String, Object> message = new HashMap<String, Object>();
				message.put("chat", data.get("chat"));
				message.put("user", data.get("user"));
				message.put("scope", "private");
				peer.deliver(getClient(), roomName, message, messageId);
				if (!peerId.equals(source.getId())) {
					// 这一条是发给当前客户的（也即是自己发给自己的），由于前台的原因，我把它给取消了
					// source.deliver(getClient(), roomName, message,
					// messageId);
				}
				return;
			}
		}
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("chat", "Unknown user: " + peerName);
		message.put("user", "SERVER");
		message.put("scope", "error");
		source.deliver(source, roomName, message, messageId);
	}
}
