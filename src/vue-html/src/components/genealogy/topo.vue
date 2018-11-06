<template>
    <div id="tp">
        <!-- <canvas ref="tp" id="canvas" width="1360" height="720"></canvas> -->
        <canvas ref="tp" id="canvas" width="750" height="521"></canvas>
    </div>
</template>
<script>
export default {
  name: "tpCanvas",
  data() {
    return {
      data: {},
      stage: {},
      scene: {}
    };
  },
  methods: {
    init() {
      let canvas = document.getElementById("canvas");
      this.stage = new JTopo.Stage(canvas);
      this.stage.centerAndZoom();
      this.stage.wheelZoom = 0.85;
      this.stage.eagleEye.visible = true;
      this.stage.mode = "drag"
      this.scene = new JTopo.Scene();
      this.stage.add(this.scene);
      this.scene.backgroundColor = "102,204,255";
      // this.scene.doLayout(JTopo.layout.TreeLayout("down", 30, 107));
    },
    createCircleNode(x, y, bean, image) {
      let node = new JTopo.Node();
      node.id= bean.id
      node.text = bean.genealogyName; // 文字
      node.textPosition = "Middle_Right"; // 文字居中
      node.font = "14px 微软雅黑"; // 字体

      node.setLocation(Math.random()*100, Math.random()*100); // 位置
      node.setSize(50, 50); // 尺寸
      node.fontColor = "0,0,0";
      node.textOffsetY = -10;
      if (image) {
        node.setImage(image);
      }
      node.layout = {type:"tree",width:200,height:100 ,direction:"top" }

     
      this.scene.add(node)

      return node;
    },
    createLink(nodeA, nodeZ, text,f){
        let link = new JTopo.FlexionalLink(nodeA, nodeZ, text)

        link.fontColor = "0,0,0"
        link.textPosition = "Bottom_Center"
        link.arrowsRadius = 15
        link.textOffsetY = 25
        link.id = nodeA.id;

        link.direction = f?"horizontal":""

        this.scene.add(link);

        return link;
    },
    createChildNode(v,node){
        let $this = this
        if(v.child){
            for (const c of v.child) {
                let childNode =  $this.createCircleNode(200,200,c,'/images/logo.png')
                $this.createLink(node,childNode,"子代")
                $this.createChildNode(c,childNode)
            }
        }
    },
    createSpouseLink(v){
        if(v.spouseId){
          let spouse = this.stage.find('node[id="'+v.spouseId+'"]');
          let pnode = this.stage.find('node[id="'+v.id+'"]');
          let otherLink = this.stage.find('link[id="'+v.spouseId+'"]');

          // spouse[0].layout = {type:"tree",width:200,height:100 ,direction:"left" }
          spouse[0].setLocation(pnode[0].x,pnode[0].y)
          if(!otherLink[0]){
            let link = this.createLink(spouse[0],pnode[0],"配偶",true)
            spouse[0].x = pnode[0].x
            spouse[0].y = pnode[0].y
            spouse[0].setLocation(pnode[0].x,pnode[0].y)
          }else if(otherLink[0].id != v.spouseId){
            let link = this.createLink(spouse[0],pnode[0],"配偶") 
            spouse[0].setLocation(pnode[0].x,pnode[0].y)
            spouse[0].x = pnode[0].x
            spouse[0].y = pnode[0].y
            spouse[0].setLocation(pnode[0].x,pnode[0].y)
            
          }
        }
        if(v.child){
          for (const c of v.child) {
            this.createSpouseLink(c)
          }
        }
    },
    loadData() {
      this.$ajax({
        method: "post",
        url: "/genealogy/person/getChild",
        data: {}
      }).then(({data:{node}}) => {
          this.scene.clear()
          let root = this.createCircleNode(100,100,{genealogyName:"起始"},'/images/logo.png')

          for (const v of node) {
              let pnode = this.createCircleNode(100,100,v,'/images/logo.png')
              if(v.child.length){
                this.createLink(root,pnode,"起点");
              }
              this.createChildNode(v,pnode)
          }
          for (const v of node) {
            this.createSpouseLink(v)
          }
          // JTopo.layout.layoutNode(this.scene,root,true)
          this.scene.doLayout(JTopo.layout.TreeLayout("down", 100, 150));
      });
    }
  },
  mounted() {
      this.init();
      this.loadData();
  }
};
</script>

<style lang="less" scoped>
#tp {
  width: 100%;
  height: 100%;
}
#canvas {
  width: 100%;
  height: 100%;
}
</style>

