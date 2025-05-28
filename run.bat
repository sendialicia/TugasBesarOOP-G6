@echo off
echo Compiling Java files...

javac -d out -cp res ^
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
src\time\*.java


if %ERRORLEVEL% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)

echo Running the program...
java -cp out;res main.Main

pause
