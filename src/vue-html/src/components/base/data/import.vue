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
    title:{
        type:String,
        default:"",
    },
    searchData:{
        type:Object,
        default:()=>{},
    },
    getImportExcelUrl:{
        type:String,
        default:"",
    },
    getTemplateUrl:{
        type:String,
        default:"",
    },
  },
  methods: {
    changeShow(){
        this.show = !this.show
    },
    beforeUpload(){
      this.importLoadingShow = true;
    },
    uploadSuccess(response, file, fileList){
      this.searchData;
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
    
    downloadTemplate(){
      window.location.href = this.getTemplateUrl+"?fileName="+this.title+".xlsx"
    },
    
  },
  mounted() {}
};
</script>
