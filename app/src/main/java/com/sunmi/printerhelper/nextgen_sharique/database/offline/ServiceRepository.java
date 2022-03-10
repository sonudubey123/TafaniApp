
package com.sunmi.printerhelper.nextgen_sharique.database.offline;

import android.app.Activity;
import android.os.AsyncTask;


import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.BulkDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.PosstockODownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.PostockSDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;

import java.util.List;

public class ServiceRepository {

    //below line is the create a variable for dao and list for all courses.
    private Dao dao;
    private List<ProductOfflineModel> allProducts;
    private List<OperatorOfflineModel> allOperators;
    private List<PrintfOfflineModal> list_printf_record;
    private List<BulkDownloadOfflineModel> list_bulkdownlaod;
    private List<ServiceOperatorDownloadOfflineModel> list_service_operator_download;
    private List<ServiceProductDownloadOfflineModel> list_service_product_download;
    private List<PosstockODownloadOfflineModel> list_posstock_o_download;
    private List<PostockSDownloadOfflineModel> list_posstock_s_download;

    ServiceDatabase database;

    //creating a constructor for our variables and passing the variables to it.
//    public ServiceRepository(Application application) {
//        ServiceDatabase database = ServiceDatabase.getInstance(application);
//        dao = database.Dao();
//        allProducts = dao.getAllProduct();
//        allOperators = dao.getAllOperators();
//    }

    public ServiceRepository(Activity application) {
        database = ServiceDatabase.getInstance(application);
        dao = database.Dao();
        allProducts = dao.getAllProduct();
        allOperators = dao.getAllOperators();
        list_printf_record = dao.getPrintfRecord();
        list_bulkdownlaod = dao.get_bulkdownload_recordList();
        list_service_operator_download = dao.get_service_operator_download();
        list_service_product_download = dao.get_service_product_download();
        list_posstock_o_download = dao.get_posstock_o_download();
        list_posstock_s_download = dao.get_posstock_s_download();


    }

    //creating a method to insert the data to our database.


    public void insert(ProductOfflineModel model) {
        new InsertProductAsyncTask(dao).execute(model);
    }

    public void insert(PrintfOfflineModal model) {
        new InsertPrintfRecordAsyncTask(dao).execute(model);
    }

    public void insert(ServiceOperatorDownloadOfflineModel model) {
        new InsertServiceOperatorDownloadAsyncTask(dao).execute(model);
    }

    public void insert(PosstockODownloadOfflineModel model) {
        new InsertPosStockODownloadAsyncTask(dao).execute(model);
    }

    public void insert(PostockSDownloadOfflineModel model) {
        new InsertPosStockSDownloadAsyncTask(dao).execute(model);
    }


    public void insert(ServiceProductDownloadOfflineModel model) {
        new InsertServiceProductDownloadAsyncTask(dao).execute(model);
    }

    public void insert(BulkDownloadOfflineModel model) {
        new InsertBulkDownloadsyncTask(dao).execute(model);
    }

    public void insert(OperatorOfflineModel model) {
        new InsertOperatorAsyncTask(dao).execute(model);
    }


    //creating a method to update data in database.
    public void update(ProductOfflineModel model) {
        new UpdateProductAsyncTask(dao).execute(model);
    }

    //creating a method to update data in database.
    public void update(PrintfOfflineModal model) {
        new UpdatePrintfRecordAsyncTask(dao).execute(model);
    }


    public void update(OperatorOfflineModel model) {
        new UpdateOperatorAsyncTask(dao).execute(model);
    }

    //creating a method to delete the data in our database.
    public void delete(ProductOfflineModel model) {
        new DeleteProductAsyncTask(dao).execute(model);
    }

    //creating a method to delete the data in our database.
    public void delete(PrintfOfflineModal model) {
        new DeletePrintfRecordAsyncTask(dao).execute(model);
    }



    public void delete(OperatorOfflineModel model) {
        new DeleteOperatorAsyncTask(dao).execute(model);
    }
    //below is the method to delete all the courses.

    public void deleteAllProducts() {
        new DeleteAllProductsAsyncTask(dao).execute();
    }

    public void deletAllServiceOperator() {
        new DeleteAllServiceOperator(dao).execute();
    }

    public void deletAllServiceProduct() {
        new DeleteAllServiceProduct(dao).execute();
    }


    public void deletAllbulkDownload() {
        new DeleteAllBulkDownloadlistAsyncTask(dao).execute();
    }



    public void deletAllYStatusbulkDownload() {
        new DeletYStatusBulkDownlaodAsyntask(dao).execute();
    }









    public void deleteAllOperators() {
        new DeleteAllOperatorssAsyncTask(dao).execute();
    }

    public void deleteAllPrintfRecordList() {
        new DeleteAllPrintfRecord_listAsyncTask(dao).execute();
    }


    public void delete_service_operator() {
        new DeleteServiceOperatorAsyncTask(dao).execute();
    }


    public void delete_service_product() {
        new DeleteServiceProductAsyncTask(dao).execute();
    }



    //below method is to read all the courses.
    public List<ProductOfflineModel> getAllProducts()
    {
        dao = database.Dao();
        allProducts = dao.getAllProduct();
        return allProducts;
    }

    //below method is to read all the courses.
    public List<PrintfOfflineModal> getList_printfOfflineModal()
    {
        dao = database.Dao();
        list_printf_record = dao.getPrintfRecord();
        return list_printf_record;
    }


   //  *************************************** BulkDownalod get All  ************************************************


    public List<BulkDownloadOfflineModel> getList_bulkdownlaod_offline()
    {
        dao = database.Dao();
        list_bulkdownlaod = dao.get_bulkdownload_recordList();
        return list_bulkdownlaod;
    }

    public void update(BulkDownloadOfflineModel model) {
        new UpdateBulkDownloadAsyncTask(dao).execute(model);
    }

    private static class UpdateBulkDownloadAsyncTask extends AsyncTask<BulkDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private UpdateBulkDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(BulkDownloadOfflineModel... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    public int update_bulkdown_pinstatus(String KEY,String DATETIME)
    {
        dao = database.Dao();
        return dao.update_bulkdown_pinstatus(KEY,DATETIME);
    }



    public List<BulkDownloadOfflineModel>  getpin_download(String product_code,String denomination_i)
    {
        dao = database.Dao();
        list_bulkdownlaod = dao.getpin_download(product_code,denomination_i);
        return list_bulkdownlaod;
    }



    public List<BulkDownloadOfflineModel>  getpin_download_statrus_y()
    {
        dao = database.Dao();
        list_bulkdownlaod = dao.getpin_download_statrus_y();
        return list_bulkdownlaod;
    }


    //creating a method to delete the data in our database.
  /*  public void delete(BulkDownloadOfflineModel model) {
        new DeleteBulkdownloadAsyncTask(dao).execute(model);
    }

    private static class DeleteBulkdownloadAsyncTask extends AsyncTask<BulkDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private DeleteBulkdownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(BulkDownloadOfflineModel... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }
*/

    //  *************************************** Service Api operator  Details All  ************************************************


    public List<ServiceOperatorDownloadOfflineModel> getList_service_operator_download_offline()
    {
        dao = database.Dao();
        list_service_operator_download = dao.get_service_operator_download();
        return list_service_operator_download;
    }



    public String get_service_operatorName()
    {
        dao = database.Dao();
        return dao.get_service_operatorName();
    }

    public int update_service_operaterName(String operater_name,String operator_code)
    {
        dao = database.Dao();
        return dao.update_service_operaterName(operater_name, operator_code);
    }

    public void update(ServiceOperatorDownloadOfflineModel model) {
        new UpdateServiceDownloadAsyncTask(dao).execute(model);
    }

    private static class UpdateServiceDownloadAsyncTask extends AsyncTask<ServiceOperatorDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private UpdateServiceDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ServiceOperatorDownloadOfflineModel... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    private static class InsertServiceOperatorDownloadAsyncTask extends AsyncTask<ServiceOperatorDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private InsertServiceOperatorDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ServiceOperatorDownloadOfflineModel... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }




    private static class DeleteAllServiceDownloadlistAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllServiceDownloadlistAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.delete_tablename_service_operator_download();
            return null;
        }
    }

    private static class DeleteServiceOperatorAsyncTask extends AsyncTask<ServiceOperatorDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private DeleteServiceOperatorAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ServiceOperatorDownloadOfflineModel... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }


    //  *************************************** Service Api Product  Details All  ************************************************


    public List<ServiceProductDownloadOfflineModel> getList_service_product_download_offline()
    {
        dao = database.Dao();
        list_service_product_download = dao.get_service_product_download();
        return list_service_product_download;
    }

    public void update(ServiceProductDownloadOfflineModel model) {
        new UpdateServiceProductDownloadAsyncTask(dao).execute(model);
    }

    private static class UpdateServiceProductDownloadAsyncTask extends AsyncTask<ServiceProductDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private UpdateServiceProductDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ServiceProductDownloadOfflineModel... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    private static class InsertServiceProductDownloadAsyncTask extends AsyncTask<ServiceProductDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private InsertServiceProductDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ServiceProductDownloadOfflineModel... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    private static class DeleteServiceProductDownloadlistAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteServiceProductDownloadlistAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.delete_tablename_service_product_download();
            return null;
        }
    }

    private static class DeleteServiceProductAsyncTask extends AsyncTask<ServiceProductDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private DeleteServiceProductAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ServiceProductDownloadOfflineModel... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    //  *************************************** Posstock O  ************************************************

    public List<PosstockODownloadOfflineModel> getList_posstock_O_download()
    {
        dao = database.Dao();
        list_posstock_o_download = dao.get_posstock_o_download();
        return list_posstock_o_download;
    }

    private static class InsertPosStockODownloadAsyncTask extends AsyncTask<PosstockODownloadOfflineModel, Void, Void> {
        private Dao dao;

        private InsertPosStockODownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PosstockODownloadOfflineModel... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }
    private static class UpdatePosstockODownloadAsyncTask extends AsyncTask<PosstockODownloadOfflineModel, Void, Void> {
        private Dao dao;

        private UpdatePosstockODownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PosstockODownloadOfflineModel... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }
    private static class DeletePosstockOAsyntask extends AsyncTask<PosstockODownloadOfflineModel, Void, Void> {
        private Dao dao;

        private DeletePosstockOAsyntask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PosstockODownloadOfflineModel... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }




    private static class DeletePosStockDownloadlistAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeletePosStockDownloadlistAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.delete_tablename_posstock_o_download();
            return null;
        }
    }
    private static class DeletePosstockSAsyntask extends AsyncTask<PostockSDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private DeletePosstockSAsyntask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PostockSDownloadOfflineModel... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    //  *************************************** Posstock S  ************************************************


    public List<PostockSDownloadOfflineModel> getList_posstock_s_download()
    {
        dao = database.Dao();
        list_posstock_s_download = dao.get_posstock_s_download();
        return list_posstock_s_download;
    }


    private static class InsertPosStockSDownloadAsyncTask extends AsyncTask<PostockSDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private InsertPosStockSDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PostockSDownloadOfflineModel... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdatePosstockSDownloadAsyncTask extends AsyncTask<PostockSDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private UpdatePosstockSDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PostockSDownloadOfflineModel... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeletePosStockSDownloadlistAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeletePosStockSDownloadlistAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.delete_tablename_posstock_s_download();
            return null;
        }
    }


    //  ***********************************************************************************************************


    public List<OperatorOfflineModel> getAllOperators() {
        dao = database.Dao();
        allOperators = dao.getAllOperators();
        return allOperators;
    }

    //we are creating a async task method to insert new course.
    private static class InsertProductAsyncTask extends AsyncTask<ProductOfflineModel, Void, Void> {
        private Dao dao;

        private InsertProductAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProductOfflineModel... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }


    private static class InsertPrintfRecordAsyncTask extends AsyncTask<PrintfOfflineModal, Void, Void> {
        private Dao dao;

        private InsertPrintfRecordAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PrintfOfflineModal... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    private static class InsertBulkDownloadsyncTask extends AsyncTask<BulkDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private InsertBulkDownloadsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(BulkDownloadOfflineModel... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }



    private static class InsertOperatorAsyncTask extends AsyncTask<OperatorOfflineModel, Void, Void> {
        private Dao dao;

        private InsertOperatorAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OperatorOfflineModel... model) {
            //below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    //we are creating a async task method to update our course.
    private static class UpdateProductAsyncTask extends AsyncTask<ProductOfflineModel, Void, Void> {
        private Dao dao;

        private UpdateProductAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProductOfflineModel... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    private static class UpdatePrintfRecordAsyncTask extends AsyncTask<PrintfOfflineModal, Void, Void> {
        private Dao dao;

        private UpdatePrintfRecordAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PrintfOfflineModal... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }
    private static class UpdateOperatorAsyncTask extends AsyncTask<OperatorOfflineModel, Void, Void> {
        private Dao dao;

        private UpdateOperatorAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OperatorOfflineModel... models) {
            //below line is use to update our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    //we are creating a async task method to delete course.
    private static class DeleteProductAsyncTask extends AsyncTask<ProductOfflineModel, Void, Void> {
        private Dao dao;

        private DeleteProductAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProductOfflineModel... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }
    private static class DeletePrintfRecordAsyncTask extends AsyncTask<PrintfOfflineModal, Void, Void> {
        private Dao dao;

        private DeletePrintfRecordAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(PrintfOfflineModal... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    private static class DeleteBulkDownloadAsyncTask extends AsyncTask<BulkDownloadOfflineModel, Void, Void> {
        private Dao dao;

        private DeleteBulkDownloadAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(BulkDownloadOfflineModel... models) {
            //below line is use to delete our course modal in dao.
        //    dao.delete(models[0]);
            return null;
        }
    }


    private static class DeleteOperatorAsyncTask extends AsyncTask<OperatorOfflineModel, Void, Void> {
        private Dao dao;

        private DeleteOperatorAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(OperatorOfflineModel... models) {
            //below line is use to delete our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    //we are creating a async task method to delete all courses.
    private static class DeleteAllProductsAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllProductsAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.deleteAllProducts();
            return null;
        }
    }

    private static class DeleteAllServiceOperator extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllServiceOperator(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.delete_tablename_service_operator_download();
            return null;
        }
    }

    private static class DeleteAllServiceProduct extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllServiceProduct(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.delete_tablename_service_product_download();
            return null;
        }
    }


    private static class DeleteAllOperatorssAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllOperatorssAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.deleteAllOperators();
            return null;
        }
    }

    private static class DeleteAllPrintfRecord_listAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllPrintfRecord_listAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.deleteAllOperators();
            return null;
        }
    }

    private static class DeleteAllBulkDownloadlistAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllBulkDownloadlistAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            //dao.delete_tablename_bulkdownload();
            return null;
        }
    }


    private static class DeletYStatusBulkDownlaodAsyntask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeletYStatusBulkDownlaodAsyntask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //on below line calling method to delete all courses.
            dao.delete_tablename_y_bulkdownload();
            return null;
        }
    }




}
