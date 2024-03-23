[Setup]
; NOTE: The value of AppId uniquely identifies this application. Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{BFB817A8-A608-472A-A3BA-B3B955219FF7}
AppName=LapBaoGia
AppVersion=1.0
;AppVerName=AppNoiThat 1.5
AppPublisher=AOne
DefaultDirName=C:\Program Files\AppNoiThat
ChangesAssociations=yes
DefaultGroupName=AppNoiThat
LicenseFile=AppNoiThat\license\license.txt
; Uncomment the following line to run in non administrative install mode (install for current user only.)
;PrivilegesRequired=lowest
OutputDir=output
OutputBaseFilename=AppNoiThatSetup
SetupIconFile=AppNoiThat\asset\logoapp.ico
Compression=lzma
SolidCompression=yes
WizardStyle=modern

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
;Source: "D:\Lap_Bao_Gia (2)\AppNoiThat\bin\AppNoiThat.exe"; DestDir: "{app}\bin"; Flags: ignoreversion
Source: "AppNoiThat\\*"; Excludes: "AppNoiThat\\bin\\Data"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs; Permissions: users-modify users-full
Source: "AppNoiThat\\bin\\Data\\*"; DestDir: "{commonappdata}\\AppNoiThat\\Data"; Flags: ignoreversion recursesubdirs createallsubdirs; Permissions: users-modify users-full 
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Registry]
Root: HKA; Subkey: "Software\Classes\.nt\OpenWithProgids"; ValueType: string; ValueName: "AppNoiThatFile.nt"; ValueData: ""; Flags: uninsdeletevalue
Root: HKA; Subkey: "Software\Classes\AppNoiThatFile.nt"; ValueType: string; ValueName: ""; ValueData: "AppNoiThat File"; Flags: uninsdeletekey
Root: HKA; Subkey: "Software\Classes\AppNoiThatFile.nt\DefaultIcon"; ValueType: string; ValueName: ""; ValueData: "{app}\bin\LapBaoGia.exe,0"
Root: HKA; Subkey: "Software\Classes\AppNoiThatFile.nt\shell\open\command"; ValueType: string; ValueName: ""; ValueData: """{app}\Launcher.exe"" ""%1"""

[Icons]
Name: "{group}\LapBaoGia"; Filename: "{app}\bin\LapBaoGia.exe"
Name: "{autodesktop}\LapBaoGia"; Filename: "{app}\bin\LapBaoGia.exe"; Tasks: desktopicon
Name: "{group}\{cm:UninstallProgram,LapBaoGia}"; Filename: "{uninstallexe}"
