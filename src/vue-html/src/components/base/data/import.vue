<style scoped lang="less">
</style>
<template>
  <Modal v-model="show" title="导入Excel数据" :mask-closable="false" width="700">
    <Row>
      <Upload
        :action="getImportExcelUrl"
        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        :show-upload-list="false"
        :before-upload="beforeUpload"
        :on-success="uploadSuccess"
      >
        <Button icon="ios-cloud-upload-outline">导入数据</Button>
      </Upload>
    </Row>
    <Row></Row>
    <Row>
      <Button icon="ios-cloud-download-outline" @click="downloadTemplate">{{title}} 模板下载</Button>
    </Row>
    <Spin fix v-if="importLoadingShow">
      <Icon type="ios-loading" size="18" class="demo-spin-icon-load"></Icon>
      <div>正在导入数据......</div>
    </Spin>
  </Modal>
</template>
<script>
export default {
  name: "iImport",
  data() {
    return {
        url:{
            importExcel:""
        },
        show:false,
        importLoadingShow:false,
    };
  },
  props: {
    //标题
    title:{
        type:String,
        default:"",
    },
    //导入接口的url
    getImportExcelUrl:{
        type:String,
        default:"",
    },
    //模板下载的url
    getTemplateUrl:{
        type:String,
        default:"",
    },
  },
  methods: {
    //打开或关闭模态窗
    changeShow(){
        this.show = !this.show
    },
    //上传开始时开启loading状态
    beforeUpload(){
      this.importLoadingShow = true;
    },
    //上传成功后，输出错误信息
    uploadSuccess(response, file, fileList){
      var errorList = response.errorList
      var errorMessage = ''
      for (const key in errorList) {
        if (errorList.hasOwnProperty(key)) {
          const element = errorList[key];
          errorMessage += "第" + element.row + "行，数据异常，" + element.value+"</br>"
        }
      }
      if(errorMessage){
        this.$Notice.error({
            title: '导入数据异常',
            desc: errorMessage,
            duration: 0
        });
      }else{
        this.$Message.success("导入成功")
      }
      this.importLoadingShow = false;
    },
    //下载模板
    downloadTemplate(){
      window.location.href = this.getTemplateUrl+"?fileName="+this.title+".xlsx"
    },
    
  },
  mounted() {}
};
</script>
