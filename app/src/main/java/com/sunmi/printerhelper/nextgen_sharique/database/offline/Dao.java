package com.sunmi.printerhelper.nextgen_sharique.database.offline;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.BulkDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.PosstockODownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.PostockSDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;

import java.util.List;

//Adding annotation to our Dao class
@androidx.room.Dao
public interface Dao {

    //below method is use to add data to database.
    @Insert
    void insert(ProductOfflineModel model);

    @Insert
    void insert(OperatorOfflineModel model);

    //below method is use to update the data in our database.
    @Update
    void update(ProductOfflineModel model);

    @Update
    void update(OperatorOfflineModel model);

    //below line is use to delete a specific course in our database.
    @Delete
    void delete(ProductOfflineModel model);

    @Delete
    void delete(OperatorOfflineModel model);

    //on below line we are making query to delete all courses from our databse.

    @Query("DELETE FROM service_product")
    void deleteAllProducts();

    @Query("DELETE FROM service_operator")
    void deleteAllOperators();

    //beloe line is to read all the courses from our database.
    //in this we are ordering our courses in ascending order with our course name.

    @Query("SELECT * FROM service_product")
    List<ProductOfflineModel> getAllProduct();

    @Query("SELECT * FROM service_operator")
    List<OperatorOfflineModel> getAllOperators();


    // #################### PRINT F   ####################

    @Query("SELECT * FROM printf_records")
    List<PrintfOfflineModal> getPrintfRecord();
    @Insert
    void insert(PrintfOfflineModal model);
    @Update
    void update(PrintfOfflineModal model);
    @Delete
    void delete(PrintfOfflineModal model);

    // #################### Bulk Download   ############################

    @Query("SELECT * FROM tableName_bulkdownload")
    List<BulkDownloadOfflineModel> get_bulkdownload_recordList();
    @Insert
    void insert(BulkDownloadOfflineModel model);
    @Update
    void update(BulkDownloadOfflineModel model);

/*    @Delete
    void delete(BulkDownloadOfflineModel model);

    @Query("DELETE FROM tableName_bulkdownload")
    void delete_tablename_bulkdownload();*/



    @Query("DELETE FROM tableName_bulkdownload WHERE status_check = 'Y'")
    void delete_tablename_y_bulkdownload();



   // @Query("UPDATE tableName_bulkdownload SET status_check = 'Y' WHERE `key` = :KEY")   // annu

    @Query("UPDATE tableName_bulkdownload SET status_check = 'Y' , sell_dateTime = :SELL_DATETIME WHERE `key` = :KEY")   // create curernt date time
    int   update_bulkdown_pinstatus(String KEY,String SELL_DATETIME);

    @Query("SELECT * FROM tableName_bulkdownload WHERE productcode= :product_code AND denomination= :denomination_i AND status_check= 'N'")
    List<BulkDownloadOfflineModel> getpin_download(String product_code,String denomination_i);


    @Query("SELECT * FROM tableName_bulkdownload WHERE  status_check= 'Y'")
    List<BulkDownloadOfflineModel> getpin_download_statrus_y();



    // #################### Service Operator Download   ############################

    @Query("SELECT * FROM tableName_service_operator")
    List<ServiceOperatorDownloadOfflineModel> get_service_operator_download();
    @Insert
    void insert(ServiceOperatorDownloadOfflineModel model);
    @Update
    void update(ServiceOperatorDownloadOfflineModel model);
    @Delete
    void delete(ServiceOperatorDownloadOfflineModel model);

    @Query("DELETE FROM tableName_service_operator")
    void delete_tablename_service_operator_download();


    // ********* Get operator name
    @Query("SELECT operatorName FROM tableName_service_operator WHERE operatorCode ='THLC' ")
    String  get_service_operatorName();


    // ********* UPDATE Service Operator Name
    @Query("UPDATE tableName_service_operator SET operatorName = :operater_name WHERE operatorCode = :operator_code")
    int   update_service_operaterName(String operater_name,String operator_code);



    // #################### Service Product Download   ############################

    @Query("SELECT * FROM tableName_service_product")
    List<ServiceProductDownloadOfflineModel> get_service_product_download();
    @Insert
    void insert(ServiceProductDownloadOfflineModel model);
    @Update
    void update(ServiceProductDownloadOfflineModel model);
    @Delete
    void delete(ServiceProductDownloadOfflineModel model);

    @Query("DELETE FROM tableName_service_product")
    void delete_tablename_service_product_download();




    // ################################# POSSTOCK O #############################################


    @Query("SELECT * FROM tableName_posstock_o")
    List<PosstockODownloadOfflineModel> get_posstock_o_download();
    @Insert
    void insert(PosstockODownloadOfflineModel model);
    @Update
    void update(PosstockODownloadOfflineModel model);
    @Delete
    void delete(PosstockODownloadOfflineModel model);

    @Query("DELETE FROM tableName_posstock_o")
    void delete_tablename_posstock_o_download();

    // ################################# POSSTOCK S #############################################


    @Query("SELECT * FROM tableName_posstock_s")
    List<PostockSDownloadOfflineModel> get_posstock_s_download();
    @Insert
    void insert(PostockSDownloadOfflineModel model);
    @Update
    void update(PostockSDownloadOfflineModel model);
    @Delete
    void delete(PostockSDownloadOfflineModel model);

    @Query("DELETE FROM tableName_posstock_s")
    void delete_tablename_posstock_s_download();




    // ##############################################################################
}