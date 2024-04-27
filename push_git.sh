#!/usr/bin/env bash
read -p "Enter your commit : " pswd
git add .
git commit -m "$pswd"
git push origin main