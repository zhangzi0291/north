<template>
  <div>

  </div>
</template>

<script>
import * as d3 from "d3"
export default {
  name: "tp",
  data() {
    return {
      zoom:{}
    }
  },
  props:{
    data: {
      type: Object,
      default:{}
    },
  },
  watch:{
    data:{
      // handler(newData, oldData) {
      //   this.init()
      // },
      // deep: true
    }
  },
  methods: {
    init() {
      var width = this.$el.clientWidth ;
      var height = this.$el.clientHeight;

      var svg = d3.select(this.$el).append("svg")
        .attr("width", width)
        .attr("height", height)
      svg.remove()
        // .attr("viewBox", "0 -50 1300 750")
      var g = svg.append("g").attr("class","container")

      var context = g.append("g").attr("class","context")

      var hierarchy = d3.hierarchy(this.data, d => {
        if(!d){
          return 
        }
        if(!!d.spouse){
          d.child.push(d.spouse)
        }
        return d.child
      });


      var tree = d3.tree().size([width-100, height-100])
        .separation(function(a, b) {
          return (a.parent == b.parent ? 1 : 2) / a.depth;
      })

      var treeData = tree(hierarchy)

      this.links(context,treeData.links())
      this.nodes(context,treeData.descendants())
      d3.select(window).on('resize.updatesvg',v=>{
        svg.remove()
        this.init()
      });

      const transform = d3.zoomIdentity.translate(0,0).scale(1)    
      this.zoom = d3.zoom()
            .scaleExtent([1 / 2, 8])
            .on('zoom', ()=>d3.select(this.$el).select('g.container').attr('transform', d3.event.transform))
      g.transition().duration(750).call(this.zoom.transform, transform)
      svg.call(this.zoom)
    },
    links(g,links){
      let linkVertical = d3.linkVertical()
            .x(function(d) { return d.x; })
            .y(function(d) { return d.y; })
      
      g.selectAll("path.links")
        .data(links)
        .enter()
        .append("path")
        .attr("class","links")
        .attr("d", function(d,i) {
          var start = { x: d.source.x, y:i==0?d.source.y+50:d.source.y };
          var end = { x: d.target.x, y: d.target.y };
          return linkVertical({ source: start, target: end });
        })
        .attr("fill", "none")
        .attr("stroke", (d)=>{
          if(d.source.data.id == d.target.data.spouseId){
            return "red"
          }
          return "black"
        })
        .attr("stroke-width", 1);
    },
    nodes(g,nodes){
      var gs = g.selectAll("g.nodes")
        .data(nodes)
        .enter()
        .append("g")
        .attr("class","nodes")
        .attr("x", (d,i) => d.x)
        .attr("y", (d,i) => i==0?d.y+50:d.y)
        .attr("transform", function(d,i) {
          var cx = d.x;
          var cy = i==0?d.y+50:d.y;
          
          return "translate(" + cx + "," + cy + ")";
        }).on("click", ()=>{
          console.log(1)
        })

      let pattern = gs.append("defs").append("pattern")
        .attr("patternContentUnits","objectBoundingBox")
        .attr("id",(d)=>d.data.id+"avatar")
        .attr("width","1")
        .attr("height","1")
        .append("image").attr("xlink:href",(d)=>d.data.genealogyAvatar?d.data.genealogyAvatar:"/images/unknow.jpg")
        .attr("width","1")
        .attr("height","1")

      gs.append("circle")
        .attr("r", 30)
        .attr("fill", (d)=>"url(#"+d.data.id+"avatar)")
        .attr("stroke", "blue")
        .attr("stroke-width", 1);
      var name = gs.append("text")
        .text(function(d) {
          return d.data.genealogyName;
        })
        .attr("text-anchor", "middle")
        .attr("y", 45)
      var  birthday= gs.append("text")
        .text(function(d) {
          if(d.data.genealogyBirthday){
            return new Date(d.data.genealogyBirthday).Format('yyyy-MM-dd')
          }
          return "未知";
        })
        .attr("text-anchor", "middle")
        .attr("y", 65)
    },
  },
  mounted() {
    this.init()
  }
};
</script>

<style lang="less">

</style>
