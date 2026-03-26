$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$sourceRoot = Join-Path $projectRoot "src\main\java"
$outputRoot = Join-Path $projectRoot "target\classes"
$toolsRoot = Join-Path $projectRoot "target\tools"
$jsonJar = Join-Path $toolsRoot "json-20250517.jar"

if (!(Test-Path $outputRoot)) {
    New-Item -ItemType Directory -Path $outputRoot | Out-Null
}

if (!(Test-Path $toolsRoot)) {
    New-Item -ItemType Directory -Path $toolsRoot | Out-Null
}

if (!(Test-Path $jsonJar)) {
    Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/json/json/20250517/json-20250517.jar" -OutFile $jsonJar
}

$sources = Get-ChildItem -Recurse -Filter *.java $sourceRoot | ForEach-Object { $_.FullName }

if (-not $sources) {
    throw "No Java source files were found in $sourceRoot"
}

javac -cp $jsonJar -d $outputRoot $sources
if ($LASTEXITCODE -ne 0) {
    throw "Compilation failed."
}

java -cp "$outputRoot;$jsonJar" com.vn.Main
