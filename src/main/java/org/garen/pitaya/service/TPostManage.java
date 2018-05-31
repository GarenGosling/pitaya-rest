package org.garen.pitaya.service;

import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.mybatis.domain.TPost;
import org.garen.pitaya.mybatis.domain.TPostDTO;
import org.garen.pitaya.mybatis.domain.TPostQuery;
import org.garen.pitaya.mybatis.service.TPostService;
import org.garen.pitaya.redis.RedisService;
import org.garen.pitaya.swagger.model.TPostVo;
import org.garen.pitaya.util.JsonMapper;
import org.garen.pitaya.util.TransferUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TPostManage extends BaseManage<Long>{
    @Autowired
    TPostService<TPost, TPostQuery, Long> service;
    @Autowired
    RedisService redisService;
    @Autowired
    TOrgManage tOrgManage;

    @Override
    public TPostService<TPost, TPostQuery, Long> getService() {
        return service;
    }

    public boolean save(TPostVo TPostVo){
        TPost TPost = new TPost();
        TransferUtil.transfer(TPost, TPostVo);
        TPost.setCreateTime(new Date());
        int i = getService().save(TPost);
        if(i == 1){
            // 更新缓存
            tOrgManage.refreshTreeRedis(tOrgManage.getTreeByDB(tOrgManage.ROOT_NODE));
            return true;
        }else{
            return false;
        }
    }

    public boolean update(TPostVo TPostVo){
        TPost TPost = new TPost();
        TransferUtil.transfer(TPost, TPostVo);
        int i = getService().updateByKey(TPost);
        if(i == 1){
            // 更新缓存
            tOrgManage.refreshTreeRedis(tOrgManage.getTreeByDB(tOrgManage.ROOT_NODE));
            return true;
        }else{
            return false;
        }
    }

    public int deleteMulti(String codes){
        int count = 0;
        for(String code : codes.split(",")){
            count = count + delete(code);
        }
        // 更新缓存
        tOrgManage.refreshTreeRedis(tOrgManage.getTreeByDB(tOrgManage.ROOT_NODE));
        return count;
    }

    private int delete(String code){
        TPostQuery TPostQuery = new TPostQuery();
        TPostQuery.Criteria criteria = TPostQuery.createCriteria();
        criteria.andCodeEqualTo(code);
        int i = getService().delete(TPostQuery);
        if(i != 1){
            throw new BadRequestException("删除失败");
        }
        return i;
    }

    public List<TPostDTO> getListAll(){
        List<TPost> TPostList = getService().findAll();
        List<TPostDTO> TPostDTOList = new ArrayList<>();
        for(TPost TPost : TPostList){
            TPostDTO TPostDTO = new TPostDTO();
            TransferUtil.transfer(TPostDTO, TPost);
            TPostDTOList.add(TPostDTO);
        }
        return TPostDTOList;
    }

    public List<TPostDTO> getByOrgCode(List<TPostDTO> all, String orgCode){
        List<TPostDTO> tPostDTOList = new ArrayList<>();
        for(TPostDTO tPostDTO : all){
            if(tPostDTO.getOrgCode().equals(orgCode)){
                tPostDTOList.add(tPostDTO);
            }
        }
        return tPostDTOList;
    }

    public TPost getByCode(String code){
        TPostQuery query = new TPostQuery();
        TPostQuery.Criteria criteria = query.createCriteria();
        criteria.andCodeEqualTo(code);
        List<TPost> tPostList = getService().findBy(query);
        if(!CollectionUtils.isEmpty(tPostList)){
            return tPostList.get(0);
        }
        return null;
    }

    public TPost getByOrgCodeAndName(String orgCode, String name){
        TPostQuery query = new TPostQuery();
        TPostQuery.Criteria criteria = query.createCriteria();
        criteria.andOrgCodeEqualTo(orgCode);
        criteria.andNameEqualTo(name);
        List<TPost> tPostList = getService().findBy(query);
        if(!CollectionUtils.isEmpty(tPostList)){
            return tPostList.get(0);
        }
        return null;
    }

}
