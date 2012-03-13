@echo ---------------------------------------------------------------------------
@echo -- Windows 下 postgreSQL 备份维护脚本----
@echo ---------------------------------------------------------------------------
 
rem 说明：
rem 1、适用于数据量不大的数据库的完全备份；
rem 2、可达到指定备份维护及保留指定备份个数的目的，结合远程可达到远程备份的目的；
rem 3、在本文件中设定后，运行中无需再次输入数据库密码；
rem 4、必要时使用压缩加密功能；
rem 5、结合任务计划可应用于生产环境
rem 6、加为计划任务可定时自动运行：
rem under WINDOWS 2003：
rem    SCHTASKS /Create /TN postgreSQL_auto_backup /TR E:\pgSQL_Backup\postgreSQL_auto_backup.bat /SC HOURLY /MO 1 /ST 00:00 /SD 2010/01/01 /ED 2012/01/01 /RU "NT AUTHORITY\SYSTEM" 
rem    under Win7：
rem    SCHTASKS /Create /TN postgreSQL_auto_backup /TR E:\pgSQL_Backup\postgreSQL_auto_backup.bat /SC HOURLY /MO 1 /ST 00:00 /ET 23:59 /SD 2010/01/01 /ED 2012/01/01 /RU "NT AUTHORITY\SYSTEM" /Z

rem 7、数据库恢复时用法：
rem    ..\bin > dropdb -U postgres postgres
rem    ..\bin > psql -U postgres -f E:\pgSQL_Backup\postgres_20100305_120000.sql -d template1


@echo off
rem 需指定变量
rem 指定pgsql用来备份的用户及密码，另一种方式是修改pg_hba.conf的认证方式等

rem 用户名
set PGUSER=postgres

rem 密码
set PGPASSWORD=123456

rem 指定备份保留个数
set preservable_Num=800

rem 指定备份数据库，可以是webdb
set backup_DB=bhtec

rem 备份前缀，避免误操作。以目标数据库作为前缀
set iPrefix=bhtec

rem 备份目录，不建议有空格，避免不同软件都需测试
set backup_to_Dir=E:\my\myProjectBackup\%date:~0,4%-%date:~5,2%-%date:~8,2%

rem pg_dump.exe的绝对路径
set pg_dump_PATH="C:\Program Files\PostgreSQL\8.4\bin\pg_dump.exe"


rem vacuumdb.exe的绝对路径
set vacuumdb_PATH="C:\Program Files\PostgreSQL\8.4\bin\vacuumdb.exe"


rem 用7z压缩，此处指定7z.exe的绝对路径
set zip_PATH=C:\Program Files\7-zip\7z.exe

rem 检查完整性
set ierr="0"
set verr="0"
set zerr="0"
set derr="0"
IF NOT EXIST %pg_dump_PATH% set ierr="1"
IF NOT EXIST %vacuumdb_PATH% set verr="1"
IF NOT EXIST "%zip_PATH%" set zerr="1"
IF NOT EXIST %backup_to_Dir% MKDIR "%backup_to_Dir%"
IF NOT EXIST %backup_to_Dir% set derr="1"

:Dir_check
IF %derr%=="0" goto pg_dump_check
ECHO %date% %time% backup_to_Dir Err!@ %backup_to_Dir% >> pg_bak_Err.txt
goto end

:pg_dump_check
IF %ierr%=="0" goto vacuumdb_check
ECHO %date% %time% pg_dump_PATH Err or pg_dump.exe file lost!@ %pg_dump_PATH% >> pg_bak_Err.txt
goto end

:vacuumdb_check
IF %verr%=="0" goto start
ECHO %date% %time% vacuumdb_PATH Err or vacuumdb.exe file lost!@ %vacuumdb_PATH% >> pg_bak_Err.txt
goto end

:zip_check
IF %zerr%=="0" goto start
ECHO %date% %time% zip_PATH Err or 7-zip.exe file lost!@ %zip_PATH% >> pg_bak_Err.txt
goto end

:start
set date_str=%date:~0,4%%date:~5,2%%date:~8,2%
if "%time:~0,1%"==" " (set time_str=0%time:~1,1%%time:~3,2%%time:~6,2%) ELSE (set time_str=%time:~0,2%%time:~3,2%%time:~6,2%)
set dbback_file=%iPrefix%_%date_str%

rem pg备份 以下任选一
%pg_dump_PATH% -i -h localhost -p 5432 -F c -b -v -f "%backup_to_Dir%\%dbback_file%.backup" %backup_DB%
%pg_dump_PATH% -C -h localhost -p 5432 -f %backup_to_Dir%\%dbback_file%.sql %backup_DB%

rem pg数据维护
%vacuumdb_PATH% -h localhost -p 5432 -d %backup_DB% -q

rem 只保留指定个数备份，基本无必要压缩处理。对应82,83行，任意选一
rem For /F "SKIP=%preservable_Num%" %%i IN ('DIR "%backup_to_Dir%%iPrefix%_*.backup" /B /TC /O-D') DO DEL "%backup_to_Dir%%%i" /Q
rem For /F "SKIP=%preservable_Num%" %%i IN ('DIR "%backup_to_Dir%%iPrefix%_*.sql" /B /TC /O-D') DO DEL "%backup_to_Dir%%%i" /Q

rem 以下为先压缩Backup文件成7zip.再Dell .Backup文件
rem %zip_PATH% a -ptjjtDs -t7z "%dbback_file%.7z" "%dbback_file%.backup" -m0=BCJ -m1=LZMA:d=21 -ms -mmt 
rem For /F "SKIP=%preservable_Num%" %%i IN ('DIR "%backup_to_Dir%%iPrefix%_*.7z" /B /TC /O-D') DO DEL "%backup_to_Dir%%%i" /Q
rem del "%dbback_file%.backup" /Q

rem 以下为先压缩Backup文件成RAR.再Dell .Backup文件。(确保你已经安装了winrar，如果你没有安装在默认目录，请根据winrar文件的位置修改路径)
rem @if exist F:\pgSQLBak\%Dirname%\%Filename% (%ProgramFiles%\winrar\winrar a -df F:\pgSQLBak\%Dirname%\%Filename%.rar F:\pgSQLBak\%Dirname%\%Filename%)
:end
rem 
rem 命令行数据库恢复实例
rem C:/Program Files/PostgreSQL/8.4/bin\pg_restore.exe --host localhost --port 5432 --username postgres --dbname t --verbose "D:\postgresqlDbBackup\bhtec_20110121_105200.backup"
@echo on
@echo ---------------------------------------------------------------------------
@echo -- postgreSQL 备份成功----
@echo ---------------------------------------------------------------------------
@echo ---------------------------------------------------------------------------
@echo -- mySql    数据库备份----
@echo ---------------------------------------------------------------------------

cd C:\Program Files\MySQL\MySQL Server 5.1\bin
C:
mysqldump --opt -uroot -p123456 bhtec>%backup_to_Dir%\%dbback_file%_m.sql
exit