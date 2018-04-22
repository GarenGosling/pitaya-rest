package org.garen.pitaya.swagger.api.valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.garen.pitaya.enums.FileType;
import org.garen.pitaya.exception.BadRequestException;
import org.garen.pitaya.service.SysUserManage;
import org.garen.pitaya.service.helper.FileHelper;
import org.garen.pitaya.swagger.model.ResponseModel;
import org.garen.pitaya.swagger.model.SysUser;
import org.garen.pitaya.util.IdNumValidUtil;
import org.garen.pitaya.util.PhoneValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SysUserValid {

    @Autowired
    SysUserManage sysUserManage;
    @Autowired
    FileHelper fileHelper;

    /**
     * 验证：新增接口
     * @param sysUser
     */
    public void validSave(SysUser sysUser){
        // 参数对象
        if(sysUser == null){
            throw new BadRequestException("新增用户不能为空");
        }
        // 非空验证：姓名
        if(StringUtils.isBlank(sysUser.getRealName())){
            throw new BadRequestException("姓名不能为空");
        }
        // 非空验证：手机号
        if(StringUtils.isBlank(sysUser.getPhone())){
            throw new BadRequestException("手机号不能为空");
        }
        // 昵称
        if(StringUtils.isNotBlank(sysUser.getNickName())){
            org.garen.pitaya.mybatis.domain.SysUser byNickName = sysUserManage.getByNickName(sysUser.getNickName());
            if(byNickName != null){
                throw new BadRequestException("昵称已存在");
            }
        }
        // 手机号
        if(!PhoneValidUtil.isPhone(sysUser.getPhone())){
            throw new BadRequestException("手机号有误");
        }
        org.garen.pitaya.mybatis.domain.SysUser byPhone = sysUserManage.getByPhone(sysUser.getPhone());
        if(byPhone != null){
            throw new BadRequestException("手机号已存在");
        }
        // 身份证
        if(StringUtils.isNotBlank(sysUser.getIdNumber())){
            if(!IdNumValidUtil.validID(sysUser.getIdNumber(), false, false)){
                throw new BadRequestException("身份证号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byIdNumber = sysUserManage.getByIdNumber(sysUser.getIdNumber());
            if(byIdNumber != null){
                throw new BadRequestException("身份证号已存在");
            }
        }
        // 微信
        if(StringUtils.isNotBlank(sysUser.getWechat())){
            org.garen.pitaya.mybatis.domain.SysUser byWechat = sysUserManage.getByWechat(sysUser.getWechat());
            if(byWechat != null){
                throw new BadRequestException("微信号已存在");
            }
        }
        // QQ
        if(StringUtils.isNotBlank(sysUser.getQq())){
            org.garen.pitaya.mybatis.domain.SysUser byQq = sysUserManage.getByQq(sysUser.getQq());
            if(byQq != null){
                throw new BadRequestException("QQ号已存在");
            }
        }
        // email
        if(StringUtils.isNotBlank(sysUser.getEmail())){
            if(sysUser.getEmail().indexOf('@')<1 || sysUser.getEmail().split("@").length>2){
                throw new BadRequestException("邮箱号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byEmail = sysUserManage.getByEmail(sysUser.getEmail());
            if(byEmail != null){
                throw new BadRequestException("邮箱号已存在");
            }
        }
    }

    /**
     * 验证：编辑接口
     * @param sysUser
     */
    public void validModify(SysUser sysUser){
        // 参数对象
        if(sysUser == null){
            throw new BadRequestException("编辑用户不能为空");
        }
        // ID(code编码)
        if(StringUtils.isBlank(sysUser.getCode())){
            throw new BadRequestException("ID不能为空");
        }
        org.garen.pitaya.mybatis.domain.SysUser byCode = sysUserManage.getByCode(sysUser.getCode());    // 原对象
        if(byCode == null){
            throw new BadRequestException("用户ID不存在");
        }
        // 昵称
        if(StringUtils.isNotBlank(sysUser.getNickName()) && !sysUser.getNickName().trim().equals(byCode.getNickName())){    // 不是空，不是原值
            org.garen.pitaya.mybatis.domain.SysUser byNickName = sysUserManage.getByNickName(sysUser.getNickName());
            if(byNickName != null){
                throw new BadRequestException("昵称已存在");
            }
        }
        // 手机号
        if(StringUtils.isNotBlank(sysUser.getPhone()) && !sysUser.getPhone().trim().equals(byCode.getPhone())){ // 不是空，不是原值
            if(!PhoneValidUtil.isPhone(sysUser.getPhone())){
                throw new BadRequestException("手机号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byPhone = sysUserManage.getByPhone(sysUser.getPhone());
            if(byPhone != null){
                throw new BadRequestException("手机号已存在");
            }
        }
        // 身份证号
        if(StringUtils.isNotBlank(sysUser.getIdNumber()) && !sysUser.getIdNumber().trim().equals(byCode.getIdNumber())){ // 不是空，不是原值
            if(!IdNumValidUtil.validID(sysUser.getIdNumber(), false, false)){
                throw new BadRequestException("身份证号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byIdNumber = sysUserManage.getByIdNumber(sysUser.getIdNumber());
            if(byIdNumber != null){
                throw new BadRequestException("身份证号已存在");
            }
        }
        // 微信
        if(StringUtils.isNotBlank(sysUser.getWechat()) && !sysUser.getWechat().trim().equals(byCode.getWechat())){  // 不是空，不是原值
            org.garen.pitaya.mybatis.domain.SysUser byWechat = sysUserManage.getByWechat(sysUser.getWechat());
            if(byWechat != null){
                throw new BadRequestException("微信号已存在");
            }
        }
        // QQ
        if(StringUtils.isNotBlank(sysUser.getQq()) && !sysUser.getQq().trim().equals(byCode.getQq())){  // 不是空，不是原值
            org.garen.pitaya.mybatis.domain.SysUser byQq = sysUserManage.getByQq(sysUser.getQq());
            if(byQq != null){
                throw new BadRequestException("QQ号已存在");
            }
        }
        // email
        if(StringUtils.isNotBlank(sysUser.getEmail()) && !sysUser.getEmail().trim().equals(byCode.getEmail())){ // 不是空，不是原值
            if(sysUser.getEmail().indexOf('@')<1 || sysUser.getEmail().split("@").length>2){
                throw new BadRequestException("邮箱号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byEmail = sysUserManage.getByEmail(sysUser.getEmail());
            if(byEmail != null){
                throw new BadRequestException("邮箱号已存在");
            }
        }
    }

    public void validImportExcel(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = inputStream = multipartFile.getInputStream();
        if(fileHelper.getType(inputStream) != FileType.XLSX){
            throw new BadRequestException("上传文件类型错误，只能上传一个.xlsx格式的Excel文件，且不超过2M");
        }
        if (multipartFile.getSize() > 2 * 1024 * 1024) {
            throw new BadRequestException("导入文件大小不能超过 2MB");
        }
    }

    public ImportExcelValidResponse validImportExcelRow(Integer rowNo, Map<Integer, String> map){
        List<String> failMsgList = new ArrayList<>();
        // 非空验证：姓名
        if(StringUtils.isBlank(map.get(1))){
            failMsgList.add("姓名为空");
        }
        // 非空验证：手机号
        if(StringUtils.isBlank(map.get(2))){
            failMsgList.add("手机号为空");
        }
        // 昵称
        if(StringUtils.isNotBlank(map.get(0))){
            org.garen.pitaya.mybatis.domain.SysUser byNickName = sysUserManage.getByNickName(map.get(0));
            if(byNickName != null){
                failMsgList.add("昵称已存在");
            }
        }
        // 手机号
        if(!PhoneValidUtil.isPhone(map.get(2))){
            failMsgList.add("手机号有误");
        }
        org.garen.pitaya.mybatis.domain.SysUser byPhone = sysUserManage.getByPhone(map.get(2));
        if(byPhone != null){
            failMsgList.add("手机号已存在");
        }
        // 身份证
        if(StringUtils.isNotBlank(map.get(3))){
            if(!IdNumValidUtil.validID(map.get(3), false, false)){
                failMsgList.add("身份证号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byIdNumber = sysUserManage.getByIdNumber(map.get(3));
            if(byIdNumber != null){
                failMsgList.add("身份证号已存在");
            }
        }
        // 微信
        if(StringUtils.isNotBlank(map.get(6))){
            org.garen.pitaya.mybatis.domain.SysUser byWechat = sysUserManage.getByWechat(map.get(6));
            if(byWechat != null){
                failMsgList.add("微信号已存在");
            }
        }
        // QQ
        if(StringUtils.isNotBlank(map.get(7))){
            org.garen.pitaya.mybatis.domain.SysUser byQq = sysUserManage.getByQq(map.get(7));
            if(byQq != null){
                failMsgList.add("QQ号已存在");
            }
        }
        // email
        if(StringUtils.isNotBlank(map.get(8))){
            if(map.get(8).indexOf('@')<1 || map.get(8).split("@").length>2){
                failMsgList.add("邮箱号有误");
            }
            org.garen.pitaya.mybatis.domain.SysUser byEmail = sysUserManage.getByEmail(map.get(8));
            if(byEmail != null){
                failMsgList.add("邮箱号已存在");
            }
        }
        ImportExcelValidResponse importExcelValidResponse = new ImportExcelValidResponse();
        importExcelValidResponse.setRowNo(rowNo);
        importExcelValidResponse.setData(map);
        importExcelValidResponse.setRes("成功");
        if(failMsgList.size() != 0){
            importExcelValidResponse.setRes("失败");
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<failMsgList.size();i++) {
                sb.append(failMsgList.get(i));
                if(i<failMsgList.size()-1){
                    sb.append("，");
                }
                if(i == failMsgList.size() - 1){
                    sb.append("。");
                }
            }
            importExcelValidResponse.setMessage(sb.toString());
        }
        return importExcelValidResponse;
    }
}
