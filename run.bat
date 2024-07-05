@echo off
chcp 65001

set "srcDir=src"
set "binDir=bin"

if not exist "%binDir%" mkdir "%binDir%"

javac -d "%binDir%" -sourcepath "%srcDir%" "%srcDir%\models\*.java" "%srcDir%\interfaces\*.java"

xcopy /y /s "%srcDir%\resources\*" "%binDir%\resources\"

cd /d "%binDir%"

java models.ATM

cd /d "%srcDir%"

pause
