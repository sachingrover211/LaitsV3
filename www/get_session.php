<?php
session_start();

//connect to database
$myFile = "../db_user_password";
$fh = fopen($myFile, 'r') or die("Could not access password file.");
$dbuser = chop(fgets($fh));
$dbpass = chop(fgets($fh));
$dbname = chop(fgets($fh));
if (strlen($dbname) == 0) {
    $dbname = 'laits_devel';
}
fclose($fh);

$mysqli = mysqli_connect("localhost", $dbuser, $dbpass, $dbname);

$id = $_GET['id'];
$groupNum = $_GET['group'];
$problemNum = $_GET['problem'];

$result = $mysqli->query("SELECT saveData FROM autosave_table WHERE id='$id' AND groupNum='$groupNum' AND problemNum='$problemNum'");

$num_rows = $result->num_rows;

if ($num_rows == 1) {
    while($row = $result->fetch_row()){
        printf("%s", $row[0]);
    }
}
?>

