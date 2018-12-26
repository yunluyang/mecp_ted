@echo off
 
rem ---------------------------------------------------------------
rem
rem ʹ��˵����
rem
rem 1: �ýű����ڱ����Ŀʱֻ��Ҫ�޸� MAIN_CLASS ��������
rem
rem 2: JAVA_OPTS ��ͨ�� -D ���� undertow.port �� undertow.host �����������
rem    �����ļ��е���ֵͬ���⻹�� undertow.resourcePath, undertow.ioThreads
rem    undertow.workerThreads �����������ͨ�� -D ���д���
rem
rem 3: JAVA_OPTS �ɴ����׼�� java �����в���,���� -Xms256m -Xmx1024m ���ೣ�ò���
rem
rem
rem ---------------------------------------------------------------
 
setlocal & pushd
 
 
rem ���������,�ýű��ļ����ڱ����ĿʱҪ������
set MAIN_CLASS=com.demo.common.DemoConfig
 
rem Java �����в���,������Ҫ�������������,�ĳ��Լ���Ҫ��,ע��Ⱥ�ǰ�����пո�
rem set "JAVA_OPTS=-Xms256m -Xmx1024m -Dundertow.port=80 -Dundertow.host=0.0.0.0"
rem set "JAVA_OPTS=-Dundertow.port=80 -Dundertow.host=0.0.0.0"
 
set APP_BASE_PATH=%~dp0
set CP=%APP_BASE_PATH%config;%APP_BASE_PATH%lib\*
java -Xverify:none %JAVA_OPTS% -cp %CP% %MAIN_CLASS%
 
 
endlocal & popd
pause