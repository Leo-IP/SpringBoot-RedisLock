<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>distributed lock</title>
</head>
<body>
<h1>Distributed lock's test</h1>
<button onclick="window.location.href = '/occupyDistributedLock'">Lock occupied</button>
<br/>
<#if success??>${success?datetime}</#if>
</body>
</html>