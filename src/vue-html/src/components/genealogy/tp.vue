<template>
  <div class="topo">

  </div>
</template>

<script>
import * as d3 from "d3"
export default {
  name: "tp",
  data() {
    return {
      zoom: {},
      width: 0,
      height: 0,
      transformNode: {},
      otherNode: undefined,
    }
  },
  props: {
    data: {
      type: Object,
      default: {}
    },
    click: {
      type: Function,
      default: function() {

      }
    },
  },
  watch: {
    data: {
      handler(newData, oldData) {
        this.data = newData
        this.init()
      },
      deep: true
    }
  },
  methods: {
    init() {
      this.width = this.$el.clientWidth;
      this.height = this.$el.clientHeight;
      d3.select("svg").remove()

      var depth = this.recursion(this.data)

      var svg = d3.select(this.$el).append("svg")
        .attr("width", this.$el.clientWidth)
        .attr("height", this.$el.clientHeight)
        .attr("viewBox", "0 -50 " + this.$el.clientWidth + " " + (this.$el.clientHeight + 60) + "")

      var g = svg.append("g").attr("class", "container")

      var context = g.append("g").attr("class", "context")
      var hierarchy = d3.hierarchy(this.data, d => {
        return d.child
      });

      var tree = d3.tree().size([this.width > 720 ? this.width : 720, depth * 200])
        .separation(function(a, b) {
          return (a.parent == b.parent ? 1 : 2) / a.depth;
        })

      var treeData = tree(hierarchy)

      this.links(context, treeData.links())
      this.nodes(context, treeData.descendants())
      d3.select(window).on('resize.updatesvg', v => {
        this.init()
      });

      const transform = d3.zoomIdentity.translate(0, 0).scale(1)
      this.zoom = d3.zoom()
        .scaleExtent([0.5, 10])
        .translateExtent([[-this.width * 1 / 3, 0], [this.width * 4 / 3, 1280]])
        // .clickDistance([50])
        .on('zoom', function() {
          let oldTransform = d3.zoomTransform(d3.select('svg').node())
          let transform = d3.zoomIdentity.translate(oldTransform.x, oldTransform.y).scale(oldTransform.k)
          return d3.select('g.container').attr('transform', d3.event.transform)
        })

      svg.call(this.zoom).on("dblclick.zoom", null)

    },
    recursion(data, depth) {
      if (!depth) {
        depth = 1;
      }
      let tempDepth = depth;
      if (data.child && data.child.length > 0) {
        for (var i = 0; i < data.child.length; i++) {
          var temp = this.recursion(data.child[i], ++depth);

          if (temp > tempDepth) {
            tempDepth = temp;
          }
        }
      }
      return tempDepth
    },
    locationPoint(x, y) {

    },
    links(g, links) {
      let linkVertical = d3.linkVertical()
        .x(function(d) { return d.x; })
        .y(function(d) { return d.y; })

      g.selectAll("path.links")
        .data(links)
        .enter()
        .append("path")
        .attr("class", "links")
        .attr("d", function(d, i) {
          var start = { x: d.source.x, y: d.source.y };
          var end = { x: d.target.x, y: d.target.y };
          return linkVertical({ source: start, target: end });
        })
        .attr("fill", "none")
        .attr("stroke", (d) => {
          if (d.source.data.id == d.target.data.spouseId) {
            return "red"
          }
          return "black"
        })
        .attr("stroke-width", 1);
    },
    nodes(g, nodes) {
      var $this = this;
      var gs = g.selectAll("g.nodes")
        .data(nodes)
        .enter()
        .append("g")
        .attr("class", "nodes")
        .attr("x", (d, i) => d.x)
        .attr("y", (d, i) => d.y)
        .attr("transform", function(d, i) {
          var cx = d.x;
          var cy = d.y;
          return "translate(" + cx + "," + cy + ")";
        }).on("click", function() {
          let x = d3.select(this).attr("x")
          let y = d3.select(this).attr("y") / 1

          if ($this.otherNode) {
            $this.otherNode.select("circle").attr("stroke", "blue")
          }
          $this.otherNode = d3.select(this);
          d3.select(this).select("circle").attr("stroke", "red")
          // console.log(x,y)
          // let transform = d3.zoomIdentity.translate(x,y).scale(1)    
          $this.zoom.translateTo(g, x, y)
          // d3.select('g.container').attr('transform', transform)
        })

      let pattern = gs.append("defs").append("pattern")
        .attr("patternContentUnits", "objectBoundingBox")
        .attr("id", (d) => d.data.id + "avatar")
        .attr("width", "1")
        .attr("height", "1")
        .append("image").attr("xlink:href", (d) => d.data.genealogyAvatar ? d.data.genealogyAvatar : "/images/unknow.jpg")
        .attr("width", "1")
        .attr("height", "1")

      gs.append("circle")
        .attr("r", 30)
        .attr("fill", (d) => "url(#" + d.data.id + "avatar)")
        .attr("stroke", "blue")
        .attr("stroke-width", 1);
      var name = gs.append("text")
        .text(function(d) {
          return d.data.genealogyName;
        })
        .attr("text-anchor", "middle")
        .attr("y", 45)
      var birthday = gs.append("text")
        .text(function(d) {
          if (d.data.genealogyBirthday) {
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
.topo {
  overflow: hidden
}
</style>
