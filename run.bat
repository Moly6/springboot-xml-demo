@echo off
@REM chcp 65001 >nul
echo ========================================
echo Spring Boot XML/JSON Demo
echo ========================================
echo.
echo 正在编译项目...
call mvn clean package -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 编译失败！
    pause
    exit /b 1
)

echo.
echo 编译成功！
echo.
echo 正在启动服务器...
echo.

java -jar target\springboot-xml-demo-1.0.0.jar

pause
