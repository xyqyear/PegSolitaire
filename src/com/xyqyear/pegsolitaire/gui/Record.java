package com.xyqyear.pegsolitaire.gui;

import java.sql.*;

public class Record {
    // singleton stuff
    private static Record singletonRecord;
    private Record(){
        init();
    }
    public static Record getInstance() {
        if (singletonRecord == null)
            singletonRecord = new Record();
        return singletonRecord;
    }

    private Connection connection = null;
    private Statement statement = null;

    private int highScore = 0;
    private boolean needUpdateHighScore = true;

    private void init() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:record.db");
            statement = connection.createStatement();

            String sql = "create table if not exists record (pieceRecord int)";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        if (needUpdateHighScore) {
            try {
                statement = connection.createStatement();
                String sql = "select count(*) as \"recordCount\" from record";
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.getInt("recordCount") != 0) {
                    sql = "select min(pieceRecord) as \"minPieceNum\" from record";
                    resultSet = statement.executeQuery(sql);
                    highScore = resultSet.getInt("minPieceNum");
                }
                statement.close();
                needUpdateHighScore = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return highScore;
    }

    public void addRecord(int pieceRecord) {
        try {
            statement = connection.createStatement();
            String sql = "insert into record(pieceRecord) values(" + pieceRecord + ")";
            statement.executeUpdate(sql);
            statement.close();
            needUpdateHighScore = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
