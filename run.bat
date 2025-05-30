@echo off
echo Compiling Java files...sd

REM Define classpath (Gson JAR and res folder)
set CLASSPATH=lib/gson-2.10.1.jar;res

javac -d out -cp %CLASSPATH% ^
src\main\*.java ^
src\entity\*.java ^
src\entity\npc\*.java ^
src\items\*.java ^
src\items\crops\*.java ^
src\items\equipments\*.java ^
src\items\fish\*.java ^
src\items\food\*.java ^
src\items\miscellaneous\*.java ^
src\items\seeds\*.java ^
src\tile\*.java ^
src\object\*.java ^
src\time\*.java ^
src\farmTile\*.java


if %ERRORLEVEL% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)

echo Running the program...
java -cp "out;lib/gson-2.10.1.jar;res" main.Main

pause