package com.identity.services;

import com.identity.pojos.FileDetails;
import com.identity.pojos.Vehicle;
import com.identity.util.Utils;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
public class VehicleDataService {
    @Autowired
    private Utils utils;

    public List<Vehicle> readFile(List<String> fileFormats, Map<String, List<FileDetails>> fileDetailsMap) {
        List<Vehicle> allVehicleList = new ArrayList<>();
        fileFormats.forEach(fileFormat -> {
            switch (fileFormat.toLowerCase()) {
                case "csv":
                    List<FileDetails> csvFileDetailsList = fileDetailsMap.get("csv");
                    csvFileDetailsList.stream()
                            .filter(Objects::nonNull).
                            forEach(file -> allVehicleList.addAll(convertTextFileToVehicleObject(file.getFilename())));
                    break;
                case "xlsx":
                    List<FileDetails> xlsFileDetailsList = fileDetailsMap.get("xlsx");
                    xlsFileDetailsList.stream()
                            .filter(Objects::nonNull).
                            forEach(file -> allVehicleList.addAll(readExcelFile(file.getFilename())));
                    break;
                case "txt":
                    List<FileDetails> txtFileDetailsList = fileDetailsMap.get("txt");
                    txtFileDetailsList.stream()
                            .filter(Objects::nonNull).
                            forEach(file -> allVehicleList.addAll(readTxtFile(file.getFilename())));
                    break;
                default:
                    throw new RuntimeException("Invalid File Format Selected.... " + fileFormat);
            }
        });


        return allVehicleList;
    }

    //    This is reading CSV files
    @SneakyThrows
    public List<Vehicle> convertTextFileToVehicleObject(String filePath) {
        return new CsvToBeanBuilder(new FileReader(filePath))
                .withType(Vehicle.class)
                .build()
                .parse();
    }


    //    this is reading an excel file and I've used POI API
    private List<Vehicle> readExcelFile(String fileName) {
        List<Vehicle> vehicleList = new ArrayList<>();
        try {
            FileInputStream excelFile = new FileInputStream(fileName);
            try {
                Workbook workbook = new XSSFWorkbook(excelFile);
                Sheet datatypeSheet = workbook.getSheetAt(0);
                Iterator<Row> iterator = datatypeSheet.iterator();
                iterator.next();
                while (iterator.hasNext()) {
                    Vehicle vehicle = new Vehicle();
                    Row currentRow = iterator.next();
                    vehicle.setRegistrationNumber(currentRow.getCell(0).getStringCellValue());
                    vehicleList.add(vehicle);
                }
            } finally {
                excelFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }
    //    this is reading a txt file and returns string
    private List<Vehicle> readTxtFile(String fileName) {
        List<Vehicle> vehicleList = new ArrayList<>();
        Set<String> vehiclesSet = utils.extractRegistrationNumber(utils.readFileGetAsString(fileName));
        vehiclesSet.forEach(v -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setRegistrationNumber(v);
            vehicleList.add(vehicle);
        });
        return vehicleList;
    }
}
