<style scoped lang="less">
.c-content {
  // height: 100%;
}
.o-echarts {
  min-height: 300px;
  // min-width: 500px;
  // width: 80%;
  height: 95%;
}
.page-content {
  height: 100%;
}
/deep/.center {
  display: flex;
  align-items: center;
  justify-content: center;
  height: calc(100% - 150px);
  // height: 100%;
}
.card-bgc {
  background: rgba(0, 0, 0, 0.3);
}
.card-title {
  text-align: center;
  font-size: 20px;
  font-weight: 1000;
}
.card-row {
  display: flex;
  // flex-flow: column;
  align-items: center;
  justify-content: center;
  flex-wrap: wrap;
  user-select: none;
  .card {
    margin: 10px;
    padding: 5px;
    width: 100px;
    text-align: center;
    &-header {
      background-color: #c9d7ea;
      padding: 10px;
    }
    &-content {
      font-size: 26px;
      font-weight: 100px;
      background-color: #c9d7ea;
    }
    &-footer {
      // background-color: #a1c0e5;
      border: 1px solid #5b9bd5;
      padding: 10px;
    }
  }
}
.divider{
  height: 90px;
}

@tabsHight:40px;
/deep/.ivu-tabs-bar{
  height: @tabsHight;
  .ivu-tabs-nav-container{
    height:@tabsHight;
    .ivu-tabs-tab{
      height: @tabsHight;
      font-size: 20px;
    }
  }
}

/deep/.ivu-tabs-nav-container{
  overflow: visible;
  .ivu-tabs-nav-wrap {
    text-align: center;
    overflow: visible;
    .ivu-tabs-nav-scroll {
      overflow: visible;
    }
    .ivu-tabs-nav-container{
      font-size:50px !important;
    }
  }
}
</style>
<template>
  <div class="c-content">
    <div class="page-title">
      <Breadcrumb>
        <BreadcrumbItem to="/home/index">首页</BreadcrumbItem>
        <BreadcrumbItem to="/statchart/chart">统计数据图表</BreadcrumbItem>
      </Breadcrumb>
    </div>

    <div class="page-content">
      <Tabs type="card" @on-click="tabChange">
        <TabPane label="福建" name="福建" >
        </TabPane>
        <TabPane label="浙江" name="浙江">
        </TabPane>
      </Tabs>

      <Row class="center">
        <div ref="person" class="o-echarts" :style="{'width':width,'height':height}"></div>
      </Row>
      <Row class="divider">
      <Divider />
      </Row>
      <Row class="center">
        <div ref="wired" class="o-echarts" :style="{'width':width,'height':height}"></div>
      </Row>
      <Row class="divider">
      <Divider />
      </Row>
      <Row class="center">
        <div ref="wireless" class="o-echarts" :style="{'width':width,'height':height}"></div>
      </Row>
      <Row class="divider">
      <Divider />
      </Row>
      <Row class="center">
        <div ref="mark" class="o-echarts" :style="{'width':width,'height':height}"></div>
      </Row>
      <Row class="divider">
      <Divider />
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
        list: "/statChart/list",
      },
      canWorkInfo: [],
      bgcolors: ["#CFDBEC", "#CEE6DE", "#D2E0CF", "#F8E29E"],
      bgcolorsfooter: ["#A7C4E6", "#9ADAB7", "#D2E0CF", "#FFD966"],
      searchData: {
        username: "",
        name: "",
        mobile: ""
      },
      chart1: null,
      chart2: null,
      chart3: null,
      chart4: null,
      data:[],
      tempData:[],
      width: "100%",
      height: "400px",
      reworkInfo: {
        cityName: "",
        data: []
      }
    };
  },
  filters: {
    oldValue(a) {
      if (isNaN(a)) {
        return 0;
      } else if (a > 0) {
        return "+" + a;
      } else {
        return a;
      }
    }
  },
  methods: {
    getOption1(data) {
      var option = {
        title: {
          text: "人员状况",
          top: "0",
          left: "0",
          textStyle: {
            fontSize: 22,
            fontWeight: 400,
          }
        },
        grid: {
          left: "1%",
          right: "4%",
          bottom: "4%",
          containLabel: true
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            crossStyle: {
              color: "#999"
            }
          },
          formatter:function(params){
            var name = params[0].name + '<br>';
            var barName = "";
            for (const key in params) {
              if (params.hasOwnProperty(key)) {
                const element = params[key];
                if(key == 0){
                  barName += element.marker+element.seriesName + ':' + element.value + '%' + '<br>';
                }else{
                  barName += element.marker+element.seriesName + ':' + element.value + '<br>';
                }
              }
            }
            return name + barName
          },
        },
        legend: {
          data: [
            "面试通过率",
            "返岗人数",
            "项目组总人数",
            "计划调动人数"
          ]
        },
        xAxis: [
          {
            type: "category",
            data: data.map( (v,i) =>{ return v.projectGroup }),
            axisLabel: {
              show: true,
              interval:0,
              rotate:-40,
            },
            axisPointer: {
              type: "shadow"
            },
            axisTick: {
                alignWithLabel: true
            },
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "百分比",
            min: 0,
            max: 100,
            splitNumber: 10,
            axisLabel: {
              formatter: "{value} %"
            }
          },
          {
            type: "value",
            name: "人数",
            min: 0,
            max: (Math.max.apply(null, data.map(info => Math.max.apply(null,[info.returnworkNum,info.projectUsernum,info.planShfitNum] ))) *1.3).toFixed(0),
            splitNumber: 10,
            axisLabel: {
              formatter: "{value} 人"
            }
          }
        ],
        series: [
          {
            name: "面试通过率",
            type: "line",
            yAxisIndex: 0,
            data: data.map( (v,i) =>{ return (v.interviewPassRate*100).toFixed(2) })
          },
          {
            name: "返岗人数",
            type: "bar",
            yAxisIndex: 1,
            data: data.map( (v,i) =>{ return v.returnworkNum })
          },
          {
            name: "项目组总人数",
            type: "bar",
            yAxisIndex: 1,
            data: data.map( (v,i) =>{ return v.projectUsernum })
          },
          {
            name: "计划调动人数",
            type: "bar",
            yAxisIndex: 1,
            data: data.map( (v,i) =>{ return v.planShfitNum })
          }
        ]
      };
      return option;
    },
    getOption2(data) {
      var option = {
        title: {
          text: "有线项目进度",
          top: "0",
          left: "0",
          textStyle: {
            fontSize: 22,
            fontWeight: 400,
          }
        },
        grid: {
          left: "1%",
          right: "4%",
          bottom: "4%",
          containLabel: true
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter:function(params){
            var name = params[0].name + '<br>';
            var barName = "";
            for (const key in params) {
              if (params.hasOwnProperty(key)) {
                const element = params[key];
                var row = data.filter(data=>data.projectGroup==element.name)
                barName += element.marker+ element.seriesName.replace("有线项目1",row[0].projectName+" ") + ':' + element.value + '%' + '<br>';

              }
            }
            return name + barName
          },
        },
        legend: {
          data: [
            "有线项目1勘察进度",
            "有线项目1会审通过进度",
            "有线项目1设计进度",
            "有线项目1平台进度"
          ]
        },
        yAxis: {
          type: "value",
          axisLabel: {
            formatter: "{value} %"
          }
        },
        xAxis: {
          type: "category",
          axisLabel: {
            show: true,
            interval:0,
            rotate:-40,
          },
          axisTick: {
                alignWithLabel: true
          },
          data: data.map( (v,i) =>{ return v.projectGroup })
        },
        series: [
          {
            name: "有线项目1勘察进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wiredPro1SurveyProgress*100).toFixed(2) })
          },
          {
            name: "有线项目1会审通过进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wiredPro1CamethroughProgress*100).toFixed(2) })
          },
          {
            name: "有线项目1设计进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wiredPro1DesignProgress*100).toFixed(2) })
          },
          {
            name: "有线项目1平台进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wiredPro1PlatformProgress*100).toFixed(2) })
          }
        ]
      };
      return option;
    },
    getOption3(data) {
      var option = {
        title: {
          text: "无线项目进度",
          top: "0",
          left: "0",
          textStyle: {
            fontSize: 22,
            fontWeight: 400,
          }
        },
        grid: {
          left: "1%",
          right: "4%",
          bottom: "4%",
          containLabel: true
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter:function(params){
            var name = params[0].name + '<br>';
            var barName = "";
            for (const key in params) {
              if (params.hasOwnProperty(key)) {
                const element = params[key];
                var row = data.filter(data=>data.projectGroup==element.name)
                barName += element.marker+ element.seriesName.replace("无线项目1",row[0].projectName+" ") + ':' + element.value + '%' + '<br>';
              }
            }
            return name + barName
          },
        },
        legend: {
          data: [
            "无线项目1勘察进度",
            "无线项目1会审通过进度",
            "无线项目1设计进度",
            "无线项目1平台进度"
          ]
        },
        yAxis: {
          type: "value",
          axisLabel: {
            formatter: "{value} %"
          }
        },
        xAxis: {
          type: "category",
          axisLabel: {
            show: true,
            interval:0,
            rotate:-40,
          },
          axisTick: {
                alignWithLabel: true
          },
          data: data.map( (v,i) =>{ return v.projectGroup })
        },
        series: [
          {
            name: "无线项目1勘察进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wirelessPro1SurveyProgress*100).toFixed(2) })          },
          {
            name: "无线项目1会审通过进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wirelessPro1CamethroughProgress*100).toFixed(2) })
          },
          {
            name: "无线项目1设计进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wirelessPro1DesignProgress*100).toFixed(2) })
          },
          {
            name: "无线项目1平台进度",
            type: "bar",
            stack: "百分比",
            // label: {
            //   show: true,
            //   position: "inside"
            // },
            data: data.map( (v,i) =>{ return (v.wirelessPro1PlatformProgress*100).toFixed(2) })
          }
        ]
      };
      return option;
    },
    getOption4(data) {
      var option = {
        title: {
          text: "口罩储备情况",
          top: "0",
          left: "0",
          textStyle: {
            fontSize: 22,
            fontWeight: 400,
          }
        },
        grid: {
          left: "1%",
          right: "4%",
          bottom: "4%",
          containLabel: true
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            crossStyle: {
              color: "#999"
            }
          },
        },
        legend: {
          data: [
            "一次性口罩（个）-项目组剩余量",
            "一次性口罩 - 人均可使用天"
          ]
        },
        yAxis: [
          {
            type: "value",
            boundaryGap: [0, 0.01],
            splitNumber: 10,
            axisLabel: {
              formatter: "{value} 个"
            }
          },
          {
            type: "value",
            boundaryGap: [0, 0.01],
            splitNumber: 10,
            axisLabel: {
              formatter: "{value} 天"
            }
          }
        ]
        ,
        xAxis: {
          type: "category",
           axisLabel: {
            show: true,
            interval:0,
            rotate:-40,
          },
          axisTick: {
                alignWithLabel: true
          },
          data: data.map( (v,i) =>{ return v.projectGroup })
        },
        series: [
          {
            name: "一次性口罩（个）-项目组剩余量",
            type: "bar",
            data: data.map( (v,i) =>{ return v.maskRemain })
          },
          {
            name: "一次性口罩 - 人均可使用天",
            type: "bar",
            yAxisIndex: 1,
            data: data.map( (v,i) =>{ return Math.floor(v.maskPercapita/2) })
          }
        ]
      };
      return option;
    },
    initEchart(echartRef, echartObj, option) {
      let $this = this;
      // this.width = (this.$refs.platform.clientHeight+"px")

      echartObj = echarts.init(echartRef);
      echarts.registerMap("福建", fujian);
      echartObj.setOption(option);

      window.addEventListener("resize", () => {
        if (echartObj && echartObj.resize) {
          echartObj.resize();
        }
      });
      // echartObj.on('click', function (params) {
      //   $this.reworkInfo.cityName = params.name
      //   $this.reworkInfo.data = params.data.data
      // });
    },
    getData() {
      let $this = this;
      this.$ajax({
        method: "post",
        url: this.url.list,
        data: {
          size:9999
        }
      }).then(function(res) {
        Object.assign($this.data, res.data.rows)
        
        $this.initEcharts("福建",$this.data)

      });
    },
    initEcharts(province,data){
      var tempData = []
      for (const key in data) {
        if (data.hasOwnProperty(key)) {
          const element = data[key];
          if(element.city.indexOf(province) != -1){
            tempData.push(element);
          }
        }
      }
      this.initEchart(this.$refs.person, this.chart1, this.getOption1(tempData));
      this.initEchart(this.$refs.wired, this.chart2, this.getOption2(tempData));
      this.initEchart(this.$refs.wireless, this.chart3, this.getOption3(tempData));
      this.initEchart(this.$refs.mark, this.chart4, this.getOption4(tempData));
    },
    tabChange(name){
      this.initEcharts(name,this.data)
    }
  },
  mounted() {
    this.getData();
  }
};
</script>
