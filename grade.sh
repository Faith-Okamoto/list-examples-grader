# make script quit if it has an error - mostly for debugging purposes
set -e

# set up directory with student submission
rm -rf student-submission
git clone --quiet $1 student-submission
cp TestListExamples.java student-submission
cd student-submission

# check if the ListExamples.java file matches the given specifications
if [[ ! -f ListExamples.java ]]
then
    echo "ListExamples.java file not found at top level; score is 0."
    exit 0
fi
if [[ $(grep -c "interface StringChecker { boolean checkString(String s); }" ListExamples.java) -eq 0 ]]
then
    echo "ListExamples.java does not have the correct StringChecker interface; score is 0."
    exit 0
fi
if [[ $(grep -Ec 'static List<String> filter\(List<String>.+, StringChecker.+\)' ListExamples.java) -eq 0 ]]
then
    echo "ListExamples.java does not have a filter method with the expected signature; score is 0."
    exit 0
fi
if [[ $(grep -Ec 'static List<String> merge\(List<String>.+, List<String>.+\)' ListExamples.java) == 0 ]]
then
    echo "ListExamples.java does not have a merge method with the expected signature; score is 0."
    exit 0
fi

# attempt compilation of ListExamples.java (unset -e because this might error)
set +e
# redirect error output so it doesn't print
javac ListExamples.java 2> error.txt
EXIT=$?
set -e

# make sure the ListExamples class compiled
if [[ $EXIT -ne 0 ]]
then
    echo "ListExamples.java failed to compile (error with code $EXIT); score is 0."
    exit 0
fi
if [[ ! -f ListExamples.class ]]
then
    echo "ListExamples class not found; score is 0."
    exit 0
fi

# compile tester
CP=".;../lib/hamcrest-core-1.3.jar;../lib/junit-4.13.2.jar" 
javac -cp $CP TestListExamples.java
# run tester (unset -e because this might error)
set +e
java -cp $CP org.junit.runner.JUnitCore TestListExamples > test.txt
set -e

# detect number of passed tests; report score
if [[ $(grep -c "^OK" test.txt) -eq 1 ]]
then
    TESTS=$(grep -Po "\d+ test" test.txt | grep -Po "\d+")
    echo "All tests passed; score is ${TESTS}."
else
    TESTS=$(grep -Po "Tests run: \d+" test.txt | grep -Po "\d+")
    FAILED=$(grep -Po "Failures: \d+" test.txt | grep -Po "\d+")
    PASSED=$((TESTS - FAILED))
    echo "${PASSED} out of ${TESTS} tests passed; score is ${PASSED}."
fi