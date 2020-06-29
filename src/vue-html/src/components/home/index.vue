<style scoped lang="less">
.c-content{
  height: 100%;
}
.o-echarts {
  min-height: 400px;
  // min-width: 500px;
  // width: 80%;
  height: 100%;
}
.page-content{
  height: 100%;
}
/deep/.center{
  display: flex;
  align-items:center;
  justify-content:center;
  height: calc(100% - 150px);
  // height: 100%;
}
.card-bgc{
  background: rgba(0,0,0,0.3)
}
.card-title{
  text-align: center;
  font-size: 20px;
  font-weight: 1000;
}
.card-row{
  display: flex;
  // flex-flow: column;
  align-items:center;
  justify-content:center;
  flex-wrap: wrap;
  user-select:none;
  .card{
    margin: 10px;
    padding: 5px;
    width: 100px;
    text-align: center;
    &-header{
      background-color: #c9d7ea;
      padding: 10px;
    }
    &-content{
      font-size: 26px;
      font-weight: 100px;
      background-color: #c9d7ea;

    }
    &-footer{
      // background-color: #a1c0e5;
      border: 1px solid #5b9bd5;
      padding: 10px;

    }
  }
}
/deep/ .profile{
  display: inline-block;
  // background: linear-gradient(to top, #4190da 0%,#f8fafc 100%);
  .title{
    height: 20px;
    position: relative;
    .title-text{
      padding: 0 10px;
      border-right:2px solid #000;
      border-left:2px solid #000;
      border-radius: 5px;
      position: absolute;
      left: 45%;
    }
    .button{
      position: absolute;
      right: 5px; 
    }
  }
  table{
    font-size: 12px;
  }
  table td{
    width: 80px;
  }
  table th{
    width: 80px;
    word-wrap:break-word;  
    word-break:break-all;
  }
}
</style>
<template>
  <div class="c-content">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home/index">首页</BreadcrumbItem>
        <!-- <BreadcrumbItem to="/rework/gisstatistic">GIS统计</BreadcrumbItem> -->
      </Breadcrumb>
    </div>

    <div class="page-content " >
      <Row class="center" style="height:100%" >
          <Col span="12" style="height:100%">
            <div ref="fujian" class="o-echarts" :style="{'width':width}"></div>
          </Col>
         <Col span="12" style="height:100%">
            <div ref="zhejiang" class="o-echarts" :style="{'width':width}"></div>
          </Col>
      </Row>
    </div>

    <div class="profile" v-if="1!=1">
        <div class="title">
          <span class="title-text">{{reworkInfo.cityName}}概况</span>
          <span class="button" @click="goDetail(reworkInfo.cityName)"></span>
        </div>
        <div>
          <table cellspacing="10px">
            <thead >
                <tr>
                    <th>项目组</th>
                    <th>在岗需求比</th>
                    <th>返岗需求比</th>
                    <th>总人数</th>
                    <th>口罩剩余量</th>
                    <th>口罩人均可使用天</th>
                </tr>
            </thead>
            <tbody align="center">
                <tr v-for="item in reworkInfo.data"  >
                  <td>{{item.projectGroup}}</td>
                  <td>{{(item.onworkProp*100).toFixed(2)}}</td>
                  <td>{{(item.returnworkProp*100).toFixed(2)}}</td>
                  <td>{{item.projectUsernum}}</td>
                  <td>{{item.maskRemain}}</td>
                  <td>{{Math.floor(item.maskPercapita/2)}}</td>
                </tr>
            </tbody>
          </table>
        </div>
      </div>

  </div>
</template>
<script>
import echarts from "echarts";
import fujian from "echarts/map/json/province/fujian.json";
import zhejiang from "echarts/map/json/province/zhejiang.json";
export default {
  name: "homeindex",
  data() {
    return {
      url: {
        getProvinceInfo: "/statChart/getProvinceInfo"
      },
      canWorkInfo:[],
      bgcolors:["#CFDBEC","#CEE6DE","#D2E0CF","#F8E29E"],
      bgcolorsfooter:["#A7C4E6","#9ADAB7","#D2E0CF","#FFD966"],
      searchData: {
        username: "",
        name: "",
        mobile: ""
      },
      echartFJ: null,
      echartZJ: null,
      width: '100%',
      reworkInfo:{
        cityName:'',
        data:[]
      }

    };
  },
  filters:{
    oldValue(a){
      if(isNaN(a)){
        return 0
      }else if (a>0){
        return "+"+a
      }else{
        return a
      }
    }
  },
  methods: {
    getOption(cityName,data,tooltipPosition){
        let cityData = []
        Object.keys(data).forEach(v =>{
            var cn = data[v][0].city
            if(cn.indexOf(cityName) == -1){
                return
            }
            cn = cn.replace(cityName,"")+"市"
            cityData.push( {"name":cn,"data":data[v]})
        })
        this.reworkInfo = cityData[0]
        let option =  {
            title: {
            // 设置标题
            text: cityName,
            top: "3%",
            left: "5%",
            textStyle: {
                fontSize: 22,
                fontWeight: 400,
                // color: "#b6d7ff"
            }
            },
            geo: {
            // 设置地图的显示信息
              map: cityName, // 注意  哪个区域的就显示哪个区域的名称
              label: {
                  normal: {
                  // 设置字体相关信息
                  show: true,
                  color: "#000",
                  },
                  emphasis: {
                  // 设置鼠标移上去hover效果
                  show: true,
                  color: "#fff"
                  }
              },
              roam: false,
              itemStyle: {
                  // 设置地图块的相关显示信息
                  normal: {
                  areaColor: "#fffcd1",
                  borderColor: "#6367ad",
                  borderWidth: 1
                  },
                  emphasis: {
                  areaColor: "#5096d6" // hover效果
                  }
              },
              left: "5%",
              right: "10%",
              top: "5%",
              bottom: "5%",
            },
            tooltip: {
                // 设置鼠标移上去的弹框显示效果
                padding: 0,
                // enterable:true,
                // confine:true,
                triggerOn: 'click',
                // backgroundColor: "transparent",
                formatter: params => {
                  console.log(params)
                    // params.data
                    if(!params.data){
                      return
                    }
                    let tr = this.getTr(params.data.data)
                    return `<div class="profile" >
                                <div class="title">
                                <span class="title-text">`+params.name+`概况</span>
                                </div>
                                <div>
                                <table cellspacing="10px">
                                    <thead >
                                        <tr>
                                            <th>项目组</th>
                                            <th>总人数</th>
                                            <th>面试通过率</th>
                                            <th>返岗人数</th>
                                            <th>计划调动人数</th>
                                            <th>口罩剩余量</th>
                                            <th>口罩人均<br>可使用天</th>
                                            <th>发热人数</th>
                                        </tr>
                                    </thead>
                                    <tbody align="center">
                                        `
                                        + tr +
                                        `
                                    </tbody>
                                </table>
                                </div>
                            </div>`;
                },
                // position: [tooltipPosition,'50%','50%'],
                position: function(point, params, dom, rect, size){ // point: 鼠标位置
                  var obj = {}
                  obj[tooltipPosition] = -document.body.offsetWidth/3;
                  return obj
                },
            },
            series: [
                {
                    // 地图块的相关信息
                    name: "地市数据",
                    type: "map",
                    geoIndex: 0, // 不可缺少，否则无tooltip 指示效果
                    data: cityData
                }
            ]
        }

        return option;
    },
    getProvinceInfo(){
      let $this = this;
      this.$ajax({
        method: "post",
        url: this.url.getProvinceInfo,
      }).then(function(res) {
        $this.initEcharts("福建",fujian,$this.$refs.fujian,$this.echartFJ,$this.getOption("福建",res.data.data,"right"))
        $this.initEcharts("浙江",zhejiang,$this.$refs.zhejiang,$this.echartZJ,$this.getOption("浙江",res.data.data,"left"))
      });
    },
    initEcharts(cityName,cityjson,echartRef, echartObj, option){
      let $this = this;

      echartObj = echarts.init(echartRef);
      echarts.registerMap(cityName, cityjson);

      echartObj.setOption(option);
      
      window.addEventListener('resize', () => {
          if (echartObj && echartObj.resize) {
              echartObj.resize();
          }
      });

    },
    getTr(data){
      let tr = ``;
      for (const key in data) {
        var object = data
        if (object.hasOwnProperty(key)) {
          const element = object[key];
          tr += `<tr>
                <td>`+(element.projectGroup==undefined?'':element.projectGroup)+`</td>
                <td>`+(element.projectUsernum==undefined?'':element.projectUsernum)+`</td>
                <td>`+(element.interviewPassRate==undefined?'':(element.interviewPassRate*100).toFixed(2))+`%</td>
                <td>`+(element.returnworkNum==undefined?'':element.returnworkNum)+`</td>
                <td>`+(element.planShfitNum==undefined?'':element.planShfitNum)+`</td>
                <td>`+(element.maskRemain==undefined?'':element.maskRemain)+`</td>
                <td>`+(element.maskPercapita==undefined?'':element.maskPercapita)+`</td>
                <td>`+(element.countFever==undefined?'0':element.countFever)+`</td>
                </tr>`
        }
      }
      return tr;
    }
  },
  mounted() {
    this.getProvinceInfo()
    
  }
};
</script>
