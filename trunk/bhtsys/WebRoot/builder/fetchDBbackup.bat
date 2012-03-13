@echo off
set ftpUser=jacob
set ftpPass=bhtecjacob
set ftpIP=192.168.1.244
set ftpFolder=/opt/PostgreSQL/dbbackup
set DownFolder=d:\tmp

rem 假设系统日期格式为yyyy-mm-dd
set Year=%date:~0,4%
set Month=%date:~5,2%
set Day=%date:~8,2%

set ftpFile=%temp%\TempAcc.txt
>"%ftpFile%" (
  echo %ftpUser%
  echo %ftpPass%
  echo cd "%ftpFolder%"
  echo bin
  echo get "bhtec_%Year%-%Month%-%Day%.tar.gz"
  echo bye
)
cd /d %DownFolder%
ftp -v -i -s:%ftpFile% %ftpIP%
goto :eof
