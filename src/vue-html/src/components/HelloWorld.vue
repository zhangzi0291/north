<style scoped lang="less">
.c-content{
  height: 100%;
}
.o-echarts {
  min-height: 300px;
  // min-width: 500px;
  // width: 80%;
  height: 95%;
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
</style>
<template>
  <div class="c-content">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home/index">首页</BreadcrumbItem>
        <!-- <BreadcrumbItem to="/rework/gisstatistic">GIS统计</BreadcrumbItem> -->
      </Breadcrumb>
    </div>

    <div class="page-content" >
      <Row class="card-bgc">
        <Row class="card-title">已返回驻地人员可现场上班人员数量情况</Row>
        <Row class="card-row">
          <div class="card" v-for="(item,i) in canWorkInfo" >
            <div class="card-header" :style="{'background-color': (bgcolors[i%4])}" >较昨日 {{(item.value - item.yesterdayValue)|oldValue}}</div>
            <div class="card-content" :style="{'background-color': (bgcolors[i%4])}">{{item.value}}</div>
            <div class="card-footer" :style="{'background-color': (bgcolorsfooter[i%4])}">{{item.name=='福州市'?"省公司":item.name}}</div>
          </div>
        </Row>
      </Row>
      <Row class="center">
        <div ref="platform" class="o-echarts" :style="{'width':width}"></div>
      </Row>
    </div>
  </div>
</template>
<script>
import echarts from "echarts";
import fujian from "echarts/map/json/province/fujian.json";
export default {
  name: "homeindex",
  data() {
    return {
      url: {
        list: "/sys/user/list",
        add: "/sys/user/add",
        get: "/sys/user/get",
        edit: "/sys/user/edit",
        del: "/sys/user/del",
        getCanWorkInfo: "/reworkWoker/getCanWorkInfo"

      },
      canWorkInfo:[],
      bgcolors:["#CFDBEC","#CEE6DE","#D2E0CF","#F8E29E"],
      bgcolorsfooter:["#A7C4E6","#9ADAB7","#D2E0CF","#FFD966"],
      searchData: {
        username: "",
        name: "",
        mobile: ""
      },
      echartObj: null,
      width: '500px',
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
    getOption(){
      let option =  {
        title: {
          // 设置标题
          text: "",
          top: "3%",
          left: "5%",
          textStyle: {
            fontSize: 22,
            fontWeight: 400,
            // color: "#b6d7ff"
          }
        },
        legend: {
          orient: "vertical",
          top: "9%",
          left: "5%",
          icon: "circle",
          data: [],
          selectedMode: "single",
          selected: {},
          itemWidth: 12,
          itemHeight: 12,
          itemGap: 30,
          inactiveColor: "#b6d7ff",
          textStyle: {
            color: "#ec808d",
            fontSize: 14,
            fontWeight: 300,
            padding: [0, 0, 0, 15]
          }
        },
        visualMap: {
          // 设置地图范围值显示的颜色
          min: 0,
          max: Math.max.apply(Math, this.canWorkInfo.map(info => info.value)),
          show: true,
          inRange: {
            color: [
              "#14861d",
              "#4caf50",
              "#d81e1e",
              "#f52323"
            ].reverse()
          },
          text : [ '多', '少' ],
          textStyle: {
            // color: "#fff"
          }
        },
        geo: {
          // 设置地图的显示信息
          map: "福建", // 注意  哪个区域的就显示哪个区域的名称
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
          right: "5%",
          top: "5%",
          bottom: "5%",
        },
        series: [
          {
            // 地图块的相关信息
            name: "已返回驻地人数",
            type: "map",
            
            geoIndex: 0, // 不可缺少，否则无tooltip 指示效果
            data: this.canWorkInfo
          }
        ]
      }

      return option;
    },
    getCanWorkInfo(){
      let $this = this;
      this.$ajax({
        method: "post",
        url: this.url.getCanWorkInfo,
      }).then(function(res) {
        
        $this.canWorkInfo = res.data.canWorkInfo
        console.log($this.canWorkInfo)
        $this.reworkInfo.cityName = $this.canWorkInfo[0].name
        $this.reworkInfo.data = $this.canWorkInfo[0].data
        
        $this.initEcharts()
      });
    },
    initEcharts(){
      let $this = this;
      this.width = (this.$refs.platform.clientHeight+"px")

      this.echartObj = echarts.init(this.$refs.platform);
      // this.echartObj.resize();
      echarts.registerMap('福建', fujian);

      this.echartObj.setOption(this.getOption(), true);
      
      window.addEventListener('resize', () => {
          if (this.echartObj && this.echartObj.resize) {
              this.echartObj.resize();
          }
      });
      this.echartObj.on('click', function (params) {
        $this.reworkInfo.cityName = params.name
        $this.reworkInfo.data = params.data.data

      });
    },
    getTr(data){
      let tr = ``;
      for (const key in data) {
        var object = data
        if (object.hasOwnProperty(key)) {
          const element = object[key];
          tr += `<tr><td>`+element.work_company+`</td><td>`+element.work_project+`</td><td>`+element.all_count+`</td><td>`+element.count+`</td></tr>`
        }
      }
      return tr;
    }
  },
  mounted() {
    // this.data.param = this.searchData;
    this.getCanWorkInfo()
    // this.initEcharts()
  }
};
</script>
