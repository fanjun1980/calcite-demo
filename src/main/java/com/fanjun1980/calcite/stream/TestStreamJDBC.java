package com.fanjun1980.calcite.stream;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.util.ConversionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class TestStreamJDBC {
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.calcite.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        System.setProperty("saffron.default.charset", ConversionUtil.NATIVE_UTF16_CHARSET_NAME);
        System.setProperty("saffron.default.nationalcharset",ConversionUtil.NATIVE_UTF16_CHARSET_NAME);
        System.setProperty("saffron.default.collation.name",ConversionUtil.NATIVE_UTF16_CHARSET_NAME + "$en_US");

        Properties info = new Properties();
        String jsonmodle = TestStreamJDBC.class.getResource("/bookshopStream.json").getPath();
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:calcite:model=" + jsonmodle, info);
            CalciteConnection calciteConn = connection.unwrap(CalciteConnection.class);

            ResultSet result = null;

            Statement st = connection.createStatement();

            st = connection.createStatement();
            //where b.name = '数据山'
            result = st.executeQuery("select stream * from SALES ");
            while(result.next()) {
                System.out.println(result.getObject(1)+" \t "+result.getObject(2)+" \t "+result.getObject(3)+" \t "+result.getObject(4));
            }

            result = st.executeQuery("select stream * from PRODUCT ");
            while(result.next()) {
                System.out.println(result.getString(1)+" \t "+result.getString(2));
            }
            result.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        }
}
