@echo off
echo Compiling Java files...
javac -d out -cp res src\main\*.java src\entity\*.java src\tile\*.java src\object\*.java  

if %ERRORLEVEL% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)

echo Running the program...
java -cp out;res main.Main

pause
  