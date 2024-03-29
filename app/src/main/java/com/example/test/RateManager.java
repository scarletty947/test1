package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private DBHelper dbHelper;
    private String TBNAME;
    public RateManager(Context context){
        dbHelper=new DBHelper(context);
        TBNAME=DBHelper.TB_NAME;
    }
    //添加一条记录
    public void add(RateItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("curname",item.getCurName());
        values.put("currate",item.getCurRate());
        db.insert(TBNAME,null,values);
        db.close();
    }
    //查询所有记录
    public List<RateItem>listALL(){
        List<RateItem> rateList=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            rateList=new ArrayList<RateItem>();
            while (cursor.moveToNext()){
                RateItem item=new RateItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;
    }

    //查找
    public RateItem findById(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"ID=?",new String[]{String.valueOf(id)},null,null,null );
        RateItem rateItem=null;
        if(cursor!=null&&cursor.moveToNext()){
            rateItem=new RateItem();
            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            rateItem.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return rateItem;
    }

    //列表批量添加记录
    public void addAll(List<RateItem> list){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for (RateItem item:list){
            ContentValues values=new ContentValues();
            values.put("curname",item.getCurName());
            values.put("currate",item.getCurRate());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }
    //更新一条记录
    public void  update(RateItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("curname",item.getCurName());
        values.put("currate",item.getCurRate());
        db.update(TBNAME,values,"ID=?",new String[]{String.valueOf(item.getId())});
        db.close();
    }
    //删除一条记录
    public void  delete(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,"ID=?",new String[]{String.valueOf(id)});
        db.close();
    }
    //删除全部
    public void deleteAll(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }



}
