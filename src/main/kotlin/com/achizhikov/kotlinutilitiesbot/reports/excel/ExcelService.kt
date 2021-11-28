package com.achizhikov.kotlinutilitiesbot.reports.excel

import com.achizhikov.kotlinutilitiesbot.reports.Report
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
        var rowNumberForFormulas = rowNumber + 1
        val prevData = report.olderData
        val newData = report.newerData
        val tariff = report.tariff
        val drainage = newData.bathHotWater!! + newData.bathColdWater!! + newData.kitchenColdWater!! +
                newData.kitchenHotWater!! - prevData.bathHotWater!! - prevData.bathColdWater!! -
                prevData.kitchenHotWater!! - prevData.kitchenColdWater!!


        val firstRow = sheet.createRow(rowNumber)
        firstRow.createCell(1).setCellValue("новые показания")
        firstRow.createCell(2).setCellValue("предыдущие показания")
        firstRow.createCell(3).setCellValue("расход")
        firstRow.createCell(4).setCellValue("тариф")
        firstRow.createCell(5).setCellValue("стоимость")
        firstRow.createCell(6).setCellValue("дата")
        rowNumber++
        rowNumberForFormulas++

        val secondRow = sheet.createRow(rowNumber)
        secondRow.createCell(0).setCellValue("электроэнергия")
        secondRow.createCell(1).setCellValue(newData.electricity!!.toDouble())
        secondRow.createCell(2).setCellValue(prevData.electricity!!.toDouble())
        secondRow.createCell(3).cellFormula = "B${rowNumberForFormulas}-C${rowNumberForFormulas}"
        secondRow.createCell(4).setCellValue(tariff.electricity!!.toDouble())
        secondRow.createCell(5).cellFormula = "D${rowNumberForFormulas}*E${rowNumberForFormulas}"
        secondRow.createCell(6).setCellValue(newData.date)
        secondRow.getCell(6).cellStyle = dateCellStyle
        rowNumber++
        rowNumberForFormulas++

        val thirdRow = sheet.createRow(rowNumber)
        thirdRow.createCell(0).setCellValue("[ванная] горячая вода")
        thirdRow.createCell(1).setCellValue(newData.bathHotWater!!.toDouble())
        thirdRow.createCell(2).setCellValue(prevData.bathHotWater!!.toDouble())
        thirdRow.createCell(3).cellFormula = "B${rowNumberForFormulas}-C${rowNumberForFormulas}"
        thirdRow.createCell(4).setCellValue(tariff.hotWater!!.toDouble())
        thirdRow.createCell(5).cellFormula = "D${rowNumberForFormulas}*E${rowNumberForFormulas}"
        rowNumber++
        rowNumberForFormulas++

        val fourthRow = sheet.createRow(rowNumber)
        fourthRow.createCell(0).setCellValue("[ванная] холодная вода")
        fourthRow.createCell(1).setCellValue(newData.bathColdWater!!.toDouble())
        fourthRow.createCell(2).setCellValue(prevData.bathColdWater!!.toDouble())
        fourthRow.createCell(3).cellFormula = "B${rowNumberForFormulas}-C${rowNumberForFormulas}"
        fourthRow.createCell(4).setCellValue(tariff.coldWater!!.toDouble())
        fourthRow.createCell(5).cellFormula = "D${rowNumberForFormulas}*E${rowNumberForFormulas}"
        rowNumber++
        rowNumberForFormulas++

        val fifthRow = sheet.createRow(rowNumber)
        fifthRow.createCell(0).setCellValue("[кухня] горячая вода")
        fifthRow.createCell(1).setCellValue(newData.kitchenHotWater!!.toDouble())
        fifthRow.createCell(2).setCellValue(prevData.kitchenHotWater!!.toDouble())
        fifthRow.createCell(3).cellFormula = "B${rowNumberForFormulas}-C${rowNumberForFormulas}"
        fifthRow.createCell(4).setCellValue(tariff.hotWater!!.toDouble())
        fifthRow.createCell(5).cellFormula = "D${rowNumberForFormulas}*E${rowNumberForFormulas}"
        rowNumber++
        rowNumberForFormulas++

        val sixthRow = sheet.createRow(rowNumber)
        sixthRow.createCell(0).setCellValue("[кухня] холодная вода")
        sixthRow.createCell(1).setCellValue(newData.kitchenColdWater!!.toDouble())
        sixthRow.createCell(2).setCellValue(prevData.kitchenColdWater!!.toDouble())
        sixthRow.createCell(3).cellFormula = "B${rowNumberForFormulas}-C${rowNumberForFormulas}"
        sixthRow.createCell(4).setCellValue(tariff.coldWater!!.toDouble())
        sixthRow.createCell(5).cellFormula = "D${rowNumberForFormulas}*E${rowNumberForFormulas}"
        rowNumber++
        rowNumberForFormulas++

        val seventhRow = sheet.createRow(rowNumber)
        seventhRow.createCell(0).setCellValue("водоотведение")
        seventhRow.createCell(3).setCellValue(drainage.toDouble())
        seventhRow.createCell(4).setCellValue(tariff.drainage!!.toDouble())
        seventhRow.createCell(5).cellFormula = "D${rowNumberForFormulas}*E${rowNumberForFormulas}"
        rowNumber++
        rowNumberForFormulas++

        val eighthRow = sheet.createRow(rowNumber)
        eighthRow.createCell(4).setCellValue("итого:")
        eighthRow.createCell(5).cellFormula =
            "TEXT(SUM(F${rowNumberForFormulas - 6}:F${rowNumberForFormulas - 1}), \"0,00\")"
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


