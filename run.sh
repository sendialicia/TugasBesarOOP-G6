#!/bin/bash
echo "Compiling Java files..."

# Define classpath (gunakan ":" sebagai pemisah di Unix)
CLASSPATH="lib/gson-2.10.1.jar:res"

# Compile Java files
javac -d out -cp "$CLASSPATH" \
src/main/*.java \
src/entity/*.java \
src/entity/npc/*.java \
src/items/*.java \
src/items/crops/*.java \
src/items/equipments/*.java \
src/items/fish/*.java \
src/items/food/*.java \
src/items/miscellaneous/*.java \
src/items/seeds/*.java \
src/tile/*.java \
src/object/*.java \
src/time/*.java \
src/farmTile/*.java

# Cek error
if [ $? -ne 0 ]; then
  echo "Compilation failed."
  exit 1
fi

echo "Running the program..."
java -cp "out:lib/gson-2.10.1.jar:res" main.Main
a