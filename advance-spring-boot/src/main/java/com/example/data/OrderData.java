package com.example.data;

import com.example.enumeration.FileState;
import com.example.enumeration.OrderState;
import com.example.json.SFFile;
import com.example.json.SFOrder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//TODO java doc
@Component
public final class OrderData {

    private static final Logger logger = LoggerFactory.getLogger(OrderData.class);
    private static final String datePattern = "yyyy-MM-dd HH:mm:ss";
    private static final DateFormat df = new SimpleDateFormat(datePattern);
    private List<SFOrder> orders;

    @Autowired
    public OrderData(@Value("${csv.file.location}") String csvLocation) {
        if(orders == null) {
            logger.info("CSV Location = {}", csvLocation);
            try {
                orders = populateOrder(csvLocation);
            } catch (IOException e) {
                logger.error("Cannot find or process csv file = {}. Check file location and data accuracy", csvLocation);
            } catch (ParseException e) {
                logger.error("Date value in the csv file does not match {}. Check date for accuracy", datePattern);
            }
        }
    }

    synchronized public List<SFOrder> getOrders() {
        return orders;
    }

    private List<SFOrder> populateOrder(String csvLocation) throws IOException, ParseException {
        List<Map<String, String>> csv = getListMapFromCSV(csvLocation);
        List<SFOrder> orderList = new CopyOnWriteArrayList<>();

        for (Map<String, String> map : csv) {
            logger.debug("Loading order id = {}", map.get("id"));

            SFOrder order = new SFOrder(
                    map.get("id"),
                    df.parse(map.get("created")),
                    df.parse(map.get("modified")),
                    map.get("priority").isEmpty() ? 50 : Integer.parseInt(map.get("priority")),
                    map.get("path"),
                    OrderState.DONE,
                    populateSFFile(map.get("path")));

            orderList.add(order);
        }

        orderList.sort(Comparator.comparingInt(SFOrder::getPriority));
        return orderList;
    }

    private List<SFFile> populateSFFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        List<String> files = new ArrayList<>();

        try {
            files = Files.walk(filePath)
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Cannot access folders or sub folders listed in the csv file (check read permissions)", e);
        }

        //priority starts @ 1 and increments for every additional file found
        int num = 1;
        List<SFFile> SFFiles = new ArrayList<>();

        for (String file : files) {
            logger.debug("File detected: {}", file);
            SFFile sFFile = new SFFile(
                    UUID.randomUUID().toString(),
                    num,
                    new Date(),
                    file,
                    FileState.DONE);
            num++;
            SFFiles.add(sFFile);
        }
        return SFFiles;
    }

    private List<Map<String, String>> getListMapFromCSV(final String filePath) throws IOException {
        List<Map<String, String>> records;
        File file = new File(getClass().getClassLoader().getResource(filePath).getFile());

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset());
             BufferedReader br = new BufferedReader(isr)) {

            String line = br.readLine();
            String[] headers = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
            records = br.lines()
                    .map(s -> s.split(","))
                    .map(t -> IntStream.range(0, t.length)
                            .boxed()
                            .collect(toMap(i -> headers[i], i -> t[i].trim())))
                    .collect(toList());

        } catch (NullPointerException e) {
            throw new NullPointerException("File is empty");
        }

        return records;
    }
}
