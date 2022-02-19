@echo on
@echo Compiling CheckRunner
javac CheckRunner.java
@echo ------------------------------------------------------------------
@echo Running CheckRunner
java CheckRunner 3-1 2-5 5-1 card-1234
@echo off
start check.txt
pause
@echo on
@echo ------------------------------------------------------------------
java CheckRunner 4-1 5-5 5-1 6-1 3-1 3-2 card-5678
@echo off
start check.txt
pause
@echo on
@echo ------------------------------------------------------------------
java CheckRunner 1-3 1-5 2-5 3-6 4-7 5-5 6-6 7-7 8-8 9-9 9-1
@echo off
start check.txt
pause
@echo on
@echo ------------------------------------------------------------------
cmd