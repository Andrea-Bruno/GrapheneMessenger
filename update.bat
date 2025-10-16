@echo off
echo Adding changes to Git...
git add .

echo Committing with message: "Regular Update"
git commit -m "Regular Update"

echo Pushing to origin main...
git push origin main

echo Update completed.
