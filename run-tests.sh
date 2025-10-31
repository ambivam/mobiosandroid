#!/bin/bash

echo "==============================================="
echo "Mobile Automation Framework Test Execution"
echo "==============================================="

PLATFORM=${1:-android}
EXECUTION_TYPE=${2:-local}
TAGS=${3:-@smoke}

echo "Platform: $PLATFORM"
echo "Execution Type: $EXECUTION_TYPE"
echo "Tags: $TAGS"
echo

echo "Cleaning previous test results..."
rm -rf test-output
mkdir -p test-output/{logs,screenshots,extent-reports,cucumber-reports}

echo "Starting test execution..."
mvn clean test -Dplatform=$PLATFORM -Dexecution.type=$EXECUTION_TYPE -Dcucumber.filter.tags="$TAGS"

echo
echo "Test execution completed!"
echo "Check reports at:"
echo "- Extent Reports: test-output/extent-reports/"
echo "- Cucumber Reports: test-output/cucumber-reports/"
echo "- Logs: test-output/logs/"
