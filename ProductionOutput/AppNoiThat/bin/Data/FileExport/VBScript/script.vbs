Option Explicit
On Error Resume Next

Dim objExcel, objWorkbook, objSheet, pdfPath

' Path to the Excel file (.xlsx)
Dim excelFilePath
excelFilePath = "EXEL_PATH"

' Output PDF path
pdfPath = "PDF_PATH"

' Create Excel application object
Set objExcel = CreateObject("Excel.Application")
If Err.Number <> 0 Then
    WScript.Echo "Error creating Excel object: " & Err.Description
    WScript.Quit
End If

' Open Excel workbook
Set objWorkbook = objExcel.Workbooks.Open(excelFilePath)
If Err.Number <> 0 Then
    WScript.Echo "Error opening Excel workbook: " & Err.Description
    objExcel.Quit
    WScript.Quit
End If

' Access the first worksheet
Set objSheet = objWorkbook.Worksheets(1)

' Export the worksheet as PDF
objSheet.ExportAsFixedFormat 0, pdfPath, 0, 0, 0

' Close the workbook and quit Excel
objWorkbook.Close
objExcel.Quit
