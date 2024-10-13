# README
 Instructions for running the tests

NOTICE: This was tested on Windows 11.

 Steps to run the tests:

1. Download or clone this repository
2. Download and install Node.js v20.20.13.1 from this [link](https://nodejs.org/dist/v20.13.1/node-v20.13.1-x64.msi)
3. Open a CMD/Termial in the path of the project folder downloaded from GitHub 
4. Execute `node --version` to check NodeJs is installed
5. To prepare the test for run, execute `npm install`
6. To make sure that the internal browsers are prepared execute `npx playwright install`
7. Tests can be run in 3 ways:
   1. UI: `npx playwright test --ui`, this will show test execution on real time (this won't generate a report).
   Press right-green triangle or F5 execute tests.
   2. Console: `npx playwright test`, after run is finished a report can be seen with `npx playwright show-report`, this will open a new tab with the report in the default system browser.
   3. Headed: `npx playwright test --headed`, after run is finished a report can be seen with `npx playwright show-report`, this will open a new tab with the report in the default system browser.
   
