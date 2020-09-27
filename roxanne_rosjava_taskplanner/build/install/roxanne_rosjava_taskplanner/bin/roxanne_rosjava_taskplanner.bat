@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  roxanne_rosjava_taskplanner startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and ROXANNE_ROSJAVA_TASKPLANNER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\roxanne_rosjava_taskplanner-0.1.0.jar;%APP_HOME%\lib\roxanne_rosjava_core-0.1.0.jar;%APP_HOME%\lib\rosjava-0.3.7.jar;%APP_HOME%\lib\apache_xmlrpc_server-0.3.7.jar;%APP_HOME%\lib\apache_xmlrpc_client-0.3.7.jar;%APP_HOME%\lib\apache_xmlrpc_common-0.3.7.jar;%APP_HOME%\lib\rosjava_test_msgs-0.3.0.jar;%APP_HOME%\lib\rosgraph_msgs-1.11.2.jar;%APP_HOME%\lib\nav_msgs-1.12.7.jar;%APP_HOME%\lib\tf2_msgs-0.5.20.jar;%APP_HOME%\lib\geometry_msgs-1.12.7.jar;%APP_HOME%\lib\actionlib_msgs-1.12.7.jar;%APP_HOME%\lib\std_msgs-0.5.12.jar;%APP_HOME%\lib\message_generation-0.3.3.jar;%APP_HOME%\lib\dnsjava-2.1.1.jar;%APP_HOME%\lib\com.springsource.org.apache.commons.httpclient-3.1.0.jar;%APP_HOME%\lib\com.springsource.org.apache.commons.logging-1.1.1.jar;%APP_HOME%\lib\com.springsource.org.apache.commons.net-2.0.0.jar;%APP_HOME%\lib\guava-12.0.jar;%APP_HOME%\lib\ws-commons-util-1.0.1.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\commons-lang3-3.10.jar;%APP_HOME%\lib\choco-solver-4.0.2.jar;%APP_HOME%\lib\antlr-3.5.2.jar;%APP_HOME%\lib\mongo-java-driver-3.12.4.jar;%APP_HOME%\lib\jfreechart-1.0.19.jar;%APP_HOME%\lib\netty-3.5.2.Final.jar;%APP_HOME%\lib\com.springsource.org.apache.commons.codec-1.3.0.jar;%APP_HOME%\lib\com.springsource.org.apache.commons.io-1.4.0.jar;%APP_HOME%\lib\commons-pool-1.6.jar;%APP_HOME%\lib\com.springsource.org.apache.commons.lang-2.4.0.jar;%APP_HOME%\lib\gradle_plugins-0.3.3.jar;%APP_HOME%\lib\jsr305-1.3.9.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\choco-sat-1.0.2.jar;%APP_HOME%\lib\cutoffseq-1.0.2.jar;%APP_HOME%\lib\trove4j-3.0.3.jar;%APP_HOME%\lib\automaton-1.11-8.jar;%APP_HOME%\lib\jgrapht-core-0.9.3.jar;%APP_HOME%\lib\cpprof-java-1.3.0.jar;%APP_HOME%\lib\ST4-4.0.8.jar;%APP_HOME%\lib\antlr-runtime-3.5.2.jar;%APP_HOME%\lib\jcommon-1.0.23.jar;%APP_HOME%\lib\xml-apis-1.0.b2.jar;%APP_HOME%\lib\protobuf-java-2.6.1.jar

@rem Execute roxanne_rosjava_taskplanner
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %ROXANNE_ROSJAVA_TASKPLANNER_OPTS%  -classpath "%CLASSPATH%" org.ros.RosRun %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable ROXANNE_ROSJAVA_TASKPLANNER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%ROXANNE_ROSJAVA_TASKPLANNER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
