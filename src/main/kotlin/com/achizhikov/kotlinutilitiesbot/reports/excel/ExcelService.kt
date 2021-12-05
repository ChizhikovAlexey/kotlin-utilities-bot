package com.achizhikov.kotlinutilitiesbot.reports.excel

import com.achizhikov.kotlinutilitiesbot.reports.Report
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream


fun createExcel(reports: List<Report>): File? {
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Utilities Data")

    val dateCellStyle = workbook.createCellStyle()
    val createHelper = workbook.creationHelper
    dateCellStyle.dataFormat = createHelper.createDataFormat().getFormat("dd/mm/yyyy")

    for ((index, report) in reports.withIndex()) {
        var rowNumber = index * 10
        val prevData = report.olderData
        val newData = report.newerData
        val tariff = report.tariff
        val drainage = newData.bathHotWater + newData.bathColdWater + newData.kitchenColdWater +
                newData.kitchenHotWater - prevData.bathHotWater - prevData.bathColdWater -
                prevData.kitchenHotWater - prevData.kitchenColdWater


        val firstRow = sheet.createRow(rowNumber++)
        firstRow.createCell(1).setCellValue("новые показания")
        firstRow.createCell(2).setCellValue("предыдущие показания")
        firstRow.createCell(3).setCellValue("расход")
        firstRow.createCell(4).setCellValue("тариф")
        firstRow.createCell(5).setCellValue("стоимость")
        firstRow.createCell(6).setCellValue("дата")

        val secondRow = sheet.fillDataRow(
            rowNumber++, "электроэнергия", newData.electricity, prevData.electricity, tariff.electricity
        )
        secondRow.createCell(6).setCellValue(newData.date)
        secondRow.getCell(6).cellStyle = dateCellStyle
        sheet.fillDataRow(
            rowNumber++, "[ванная] горячая вода", newData.bathHotWater, prevData.bathHotWater, tariff.hotWater
        )
        sheet.fillDataRow(
            rowNumber++, "[ванная] холодная вода", newData.bathColdWater, prevData.bathColdWater, tariff.coldWater
        )
        sheet.fillDataRow(
            rowNumber++, "[кухня] горячая вода", newData.kitchenHotWater, prevData.kitchenHotWater, tariff.hotWater
        )
        sheet.fillDataRow(
            rowNumber++, "[кухня] холодная вода", newData.kitchenHotWater, prevData.kitchenHotWater, tariff.hotWater
        )

        val seventhRow = sheet.createRow(rowNumber)
        seventhRow.createCell(0).setCellValue("водоотведение")
        seventhRow.createCell(3).setCellValue(drainage.toDouble())
        seventhRow.createCell(4).setCellValue(tariff.drainage)
        seventhRow.createCell(5).cellFormula = "D${rowNumber + 1}*E${rowNumber + 1}"
        rowNumber++

        val eighthRow = sheet.createRow(rowNumber)
        eighthRow.createCell(4).setCellValue("итого:")
        eighthRow.createCell(5).cellFormula =
            "SUM(F${rowNumber - 5}:F${rowNumber})"
        eighthRow.createCell(6).setCellValue("₽")
    }

    for (i in 0..6) {
        sheet.autoSizeColumn(i)
    }

    return try {
        val file = File("utilities.xlsx")
        val out = FileOutputStream(file)
        workbook.write(out)
        out.close()
        file
    } catch (exc: Exception) {
        null
    }
}

private fun XSSFSheet.fillDataRow(
    rowNumber: Int,
    type: String,
    value: Int,
    previousValue: Int,
    tariff: Double
): XSSFRow {
    val row = this.createRow(rowNumber)
    row.createCell(0).setCellValue(type)
    row.createCell(1).setCellValue(value.toDouble())
    row.createCell(2).setCellValue(previousValue.toDouble())
    row.createCell(3).cellFormula = "B${rowNumber + 1}-C${rowNumber + 1}"
    row.createCell(4).setCellValue(tariff)
    row.createCell(5).cellFormula = "D${rowNumber + 1}*E${rowNumber + 1}"
    return row
}