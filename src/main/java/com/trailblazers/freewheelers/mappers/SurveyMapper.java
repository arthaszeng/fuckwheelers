package com.trailblazers.freewheelers.mappers;

import com.trailblazers.freewheelers.model.NpsReport;
import com.trailblazers.freewheelers.model.SurveyEntry;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SurveyMapper {

    @Insert(
            "INSERT INTO survey_entry (account_id, rating, comment) " +
                    "VALUES (#{accountId}, #{surveyEntry.rating}, #{surveyEntry.comment})"
    )
    Integer insert(@Param(value = "accountId") Long accountId, @Param(value = "surveyEntry") SurveyEntry surveyEntry);

    @Select(
            "select count(*) as total, " +
            "count(case when rating > 8 then 1 else null end) as promoters, " +
            "count(case when rating < 7 then 1 else null end) as detractors, " +
            "count(case when rating > 6 and rating < 9 then 1 else null end) as passives " +
            "from survey_entry"
    )
    @ConstructorArgs(value = {
            @Arg(column = "promoters", javaType = int.class),
            @Arg(column = "detractors", javaType = int.class),
            @Arg(column = "passives", javaType = int.class),
            @Arg(column = "total", javaType = int.class)
    })
    public NpsReport generateNpsReport();

    @Delete(
        "DELETE FROM survey_entry"
    )
    @Options(flushCache = true)
    public void deleteAll();

    @Select("select comment from survey_entry " +
            "where rating > 8 " +
            "and comment is not null " +
            "and comment <> ''")
    public List<String> getPromoterComments();

    @Select("select comment from survey_entry " +
            "where rating < 7 " +
            "and comment is not null " +
            "and comment <> ''")
    public List<String> getDetractorComments();

    @Select("select comment from survey_entry " +
            "where rating > 6 " +
            "and rating < 9 " +
            "and comment is not null " +
            "and comment <> ''")
    public List<String> getPassiveComments();
}
