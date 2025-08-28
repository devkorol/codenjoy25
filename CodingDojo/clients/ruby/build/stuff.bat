@echo off
rem #%L
rem Codenjoy - it's a dojo-like platform from developers to developers.
rem %%
rem Copyright (C) 2012 - 2022 Codenjoy
rem %%
rem This program is free software: you can redistribute it and/or modify
rem it under the terms of the GNU General Public License as
rem published by the Free Software Foundation, either version 3 of the
rem License, or (at your option) any later version.
rem
rem This program is distributed in the hope that it will be useful,
rem but WITHOUT ANY WARRANTY; without even the implied warranty of
rem MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
rem GNU General Public License for more details.
rem
rem You should have received a copy of the GNU General Public
rem License along with this program.  If not, see
rem <http://www.gnu.org/licenses/gpl-3.0.html>.
rem #L%
@echo on

@echo off

if "%RUN%"=="" set RUN=%CD%\run
if "%STUFF%"=="" set STUFF=%CD%\stuff

call %RUN% :init_colors

:check_run_mode
    if "%*"=="" (       
        call :run_executable 
    ) else (
        call :run_library %*
    )
    goto :eof

:run_executable
    rem run stuff.bat as executable script
    call %RUN% :color ‘%CL_INFO%‘ ‘This is not executable script. Please use 'run.bat' only.‘
    call %RUN% :ask   
    goto :eof

:run_library
    rem run stuff.bat as library
    call %*     
    goto :eof          

:settings
    if "%INSTALL_LOCALLY%"=="true" ( set RUBY_HOME=)

    if "%RUBY_HOME%"==""   ( set NO_RUBY=true)
    if "%NO_RUBY%"=="true" ( set RUBY_HOME=%ROOT%\.ruby)
    if "%NO_RUBY%"=="true" ( set PATH=%RUBY_HOME%\bin;%PATH%)

    set RUBY=%RUBY_HOME%\bin\ruby.exe
    set BUNDLE=%RUBY_HOME%\bin\bundle.cmd
    set RIDK=%RUBY_HOME%\bin\ridk.cmd
    set GEM=%RUBY_HOME%\bin\gem.cmd

    echo Language environment variables
    call %RUN% :color ‘%CL_INFO%‘ ‘PATH=%PATH%‘
    call %RUN% :color ‘%CL_INFO%‘ ‘RUBY_HOME=%RUBY_HOME%‘

    set ARCH_URL=https://github.com/oneclick/rubyinstaller2/releases/download/RubyInstaller-3.0.2-1/rubyinstaller-3.0.2-1-x64.7z
    set ARCH_FOLDER=rubyinstaller-3.0.2-1-x64

    set ARCH_GEM_URL=https://rubygems.org/rubygems/rubygems-3.2.29.zip
    set ARCH_GEM_FOLDER=rubygems-3.2.29
    goto :eof

:install
    call %RUN% :install ruby %ARCH_URL% %ARCH_FOLDER%
    call %RUN% :install gem %ARCH_GEM_URL% %ARCH_GEM_FOLDER%
    cd %ROOT%\.gem
    call %RUBY% setup.rb
    cd %ROOT%
    goto :eof

:version
    call %RUN% :eval_echo_color ‘%RUBY% -v‘
    goto :eof

:build
    rem call %RUN% :eval_echo ‘%RIDK% install‘
    rem call %RUN% :eval_echo ‘%GEM% update --system‘
    rem call %RUN% :eval_echo ‘%BUNDLE% install‘
    call %RUN% :eval_echo ‘%GEM% install codenjoy-client‘
    goto :eof

:test    
    rem implement me

    call %RUN% :sep
    goto :eof

:run
    call %RUN% :eval_echo ‘%RUBY% ./game.rb %GAME_TO_RUN% %SERVER_URL%‘
    goto :eof