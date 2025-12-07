@echo off
git fetch origin && git merge -X ours origin/main
git add . && git commit -m "Regular Update %DATE%"
git push -f origin main
echo Done.