package com.zhouwei.listenlite.loader;

import com.zhouwei.listenlite.http.ObjectLoader;
import com.zhouwei.listenlite.http.RetrofitServiceManager;
import com.zhouwei.listenlite.model.KuGouRawLyric;
import com.zhouwei.listenlite.model.KuGouSearchLyricResult;
import com.zhouwei.listenlite.utils.LyricUtils;

import java.io.File;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhouwei on 17/5/12.
 */

public class LrcLoader extends ObjectLoader {
   private LrcService mLrcService;

   public LrcLoader(){
       mLrcService = RetrofitServiceManager.getInstance().create(LrcService.class);
   }


    /**
     *  获取网络歌词
     * @param songName 歌曲名
     * @param songArt 歌手
     * @param duration 时长
     * @return
     */
   public Observable<File> getLrc(final String songName, final String songArt, final long duration){
       return mLrcService.searchLyric(songName,String.valueOf(duration))
               .subscribeOn(Schedulers.io())
               .unsubscribeOn(Schedulers.io())
               .flatMap(new Func1<KuGouSearchLyricResult, Observable<KuGouRawLyric>>() {

                   @Override
                   public Observable<KuGouRawLyric> call(KuGouSearchLyricResult kuGouSearchLyricResult) {
                       if(kuGouSearchLyricResult.status == 200 && kuGouSearchLyricResult.candidates!= null
                               && kuGouSearchLyricResult.candidates.size()!= 0){
                           KuGouSearchLyricResult.Candidates candidates = kuGouSearchLyricResult.candidates.get(0);
                           return mLrcService.getRawLyric(candidates.id,candidates.accesskey);
                       }
                       return Observable.just(null);
                   }
               })
               .map(new Func1<KuGouRawLyric, File>() {
                   @Override
                   public File call(KuGouRawLyric kuGouRawLyric) {
                       if(kuGouRawLyric == null){
                           return null;
                       }
                       // 将歌词写入文件
                       String lyricContent = LyricUtils.decryptBASE64(kuGouRawLyric.content);
                       File lyricFile = LyricUtils.writeLrcToFile(songName,songArt,lyricContent);
                       return lyricFile;
                   }
               });

   }

    public interface LrcService{
        @GET("search?ver=1&man=yes&client=pc")
        Observable<KuGouSearchLyricResult> searchLyric(@Query("keyword") String songName, @Query("duration") String duration);

        @GET("download?ver=1&client=pc&fmt=lrc&charset=utf8")
        Observable<KuGouRawLyric> getRawLyric(@Query("id") String id, @Query("accesskey") String accesskey);
    }


}
