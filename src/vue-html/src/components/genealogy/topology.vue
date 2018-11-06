<template>
<Card :bordered="false">
<Tabs value="name1" type="card">
    <TabPane label="拓扑" name="name1" icon="md-people">
      <topo ref="topo"></topo>
      <!-- <canvas ref="tp" id="tp" width="1000" height="400" ></canvas> -->
    </TabPane>
    <TabPane label="列表" name="name2" icon="md-card">
        <div>
        <Form 
          inline 
          :model="searchData">
          <FormItem label-for='search'>
            <Input 
              v-model="searchData.genealogyName" 
              class="input-search" 
              placeholder="姓名" 
              clearable/>
          </FormItem>
          <FormItem>
            <Button 
              type="primary" shape="circle" 
              @click='search'>查询</Button>
              <Button type="primary" @click="openPersonShow" >BOX</Button>

            <Dropdown 
              trigger="click" 
              @on-click='dropdownFunction'>
              <Button shape="circle">
                其他功能
                <Icon type="md-arrow-dropdown"/>
              </Button>
              <DropdownMenu slot="list">
                <DropdownItem name='add'>
                  <Icon type="android-add-circle" size='18' />
                  <span class="layout-text">新增</span>
                </DropdownItem>
                <DropdownItem divided>
                  <Icon type="android-upload" size='18' />
                  <span class="layout-text">导入</span>
                </DropdownItem>
                <DropdownItem>
                  <Icon type="android-download" size='18' />
                  <span class="layout-text">导出</span>
                </DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </FormItem>
        </Form>
      </div>
      
      <t-table ref='table' :url='url.list' :param='data.param' :columns='data.columns'/>
    </TabPane>


    <Modal v-model="editshow" title="编辑" @on-ok="editok" >
        <Form ref="editModal" :model="detailDate" inline :rules='detail.rule'>
            <FormItem label="姓名：" v-model="detailDate.genealogyName" prop="genealogyName" style="width:47%;">
             <Input v-model="detailDate.genealogyName"  />
            </FormItem>
            <FormItem label="性别：" v-model="detailDate.genealogySex" prop="genealogySex" style="width:47%;" >
              <RadioGroup v-model="detailDate.genealogySex" style="width: 100%" >
                <Radio label="男"/>
                <Radio label="女"/>
              </RadioGroup>
            </FormItem>
            <FormItem label="生日："  v-model="detailDate.genealogyBirthday" prop="genealogyBirthday"  style="width:47%;">
             <DatePicker v-model="detailDate.genealogyBirthday" type="datetime" style="width: 100%"/>
            </FormItem>
            <FormItem label="卒日：" v-model="detailDate.genealogyDeadtime" prop="genealogyDeadtime"  style="width:47%;">
             <DatePicker v-model="detailDate.genealogyDeadtime" type="datetime" style="width: 100%"/>
            </FormItem>
            <FormItem label="父辈:" v-model="detailDate.parentId" prop="parentId"  style="width:47%;">
             <Input v-model="detailDate.parentId" @on-focus="openPersonShow('P')" />
            </FormItem>
            <FormItem label="配偶:" v-model="detailDate.spouseId" prop="spouseId" style="width:47%;" >
             <Input v-model="detailDate.spouseId" @on-focus="openPersonShow('S')" />
            </FormItem>
            <FormItem label="人生经历：" v-model="detailDate.genealogyExperience" prop="genealogyExperience"  style="width:100%;">
             <Input v-model="detailDate.genealogyExperience" type="textarea" :rows="4"  />
            </FormItem>
        </Form>
    </Modal>
    <Modal v-model="addshow" title="新增" @on-ok="addok" >
      <Form ref="addModal" :model="add.data" inline :rules='add.rule'>
            <FormItem label="姓名：" v-model="add.data.genealogyName" prop="genealogyName" style="width:47%;">
             <Input v-model="add.data.genealogyName"  />
            </FormItem>
            <FormItem label="性别：" v-model="add.data.genealogySex" prop="genealogySex" style="width:47%;">
              <RadioGroup v-model="add.data.genealogySex" style="width: 100%" >
                <Radio label="男"/>
                <Radio label="女"/>
              </RadioGroup>
            </FormItem>
            <FormItem label="生日：" v-model="add.data.genealogyBirthday" prop="genealogyBirthday" style="width:47%;">
             <DatePicker v-model="add.data.genealogyBirthday" type="datetime" style="width: 100%"/>
            </FormItem>
            <FormItem label="卒日：" v-model="add.data.genealogyDeadtime" prop="genealogyDeadtime" style="width:47%;">
             <DatePicker v-model="add.data.genealogyDeadtime" type="datetime" style="width: 100%"/>
            </FormItem>
            <FormItem label="父辈:" v-model="add.data.parentId" prop="parentId" style="width:47%;">
             <Input v-model="add.data.parentId" @on-focus="openPersonShow('P')" />
            </FormItem>
            <FormItem label="配偶:" v-model="add.data.spouseId" prop="spouseId" style="width:47%;">
             <Input v-model="add.data.spouseId" @on-focus="openPersonShow('S')"  />
            </FormItem>
            <FormItem label="人生经历：" v-model="add.data.genealogyExperience" prop="genealogyExperience" style="width: 100%" >
             <Input v-model="add.data.genealogyExperience" type="textarea" :rows="4"  />
            </FormItem>
      </Form>
    </Modal>

    <Modal v-model="personShow" title="请选择" @on-ok="personOk" draggable>
        <div>
        <Form inline :model="searchData2">
          <FormItem label-for='search'>
            <Input 
              v-model="searchData2.genealogyName" 
              class="input-search" 
              placeholder="姓名" 
              clearable/>
          </FormItem>
          <FormItem>
            <Button type="primary" shape="circle" @click='search2'>查询</Button>
          </FormItem>
        </Form>
        <t-table ref='table2' :url='url.list' :param='selectData.param' :columns='selectData.columns' :functions="functions"/>
      </div>
    </Modal>
</Tabs>
</Card>
</template>
<script>
import topo from "./topo"
export default {
  components:{
    topo
  },
  data() {
    return {
      searchData: {
        genealogyName: ""
      },
      searchData2: {
        genealogyName: ""
      },
      PorS:"P",
      editshow: false,
      addshow: false,
      personShow: false,
      url: {
        list: "/genealogy/person/list",
        add: "/genealogy/person/add",
        get: "/genealogy/person/get",
        edit: "/genealogy/person/edit",
        del: "/genealogy/person/del"
      },
      detail: {
        data: {},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "genealogyName", value: "姓名" },
          {
            key: "genealogySex",
            value: "性别",
            type: "select",
            child: [{ name: "男", value: "男" }, { name: "女", value: "女" }]
          },
          { key: "genealogyBirthday", value: "生日", type: "date" },
          { key: "genealogyDeadtime", value: "卒日", type: "date" },
          { key: "genealogyExperience", value: "人生经历" },
          { key: "parentId", value: "父辈" },
          { key: "spouseId", value: "配偶" }
        ],
        rule: {
          genealogyName: [
            { required: true, message: "姓名必填", trigger: "blur" }
          ],
          genealogySex: [
            { required: true, message: "性别必填", trigger: "blur" }
          ]
        }
      },
      add: {
        data: {genealogyId:this.$route.params.id},
        columns: [
          { key: "id", value: "id", hidden: true },
          { key: "genealogyName", value: "姓名" },
          {
            key: "genealogySex",
            value: "性别",
            type: "select",
            child: [{ name: "男", value: "男" }, { name: "女", value: "女" }]
          },
          { key: "genealogyBirthday", value: "生日", type: "date" },
          { key: "genealogyDeadtime", value: "卒日", type: "date" },
          { key: "genealogyExperience", value: "人生经历" },
          { key: "parentId", value: "父辈" },
          { key: "spouseId", value: "配偶" }
        ],
        rule: {
          genealogyName: [
            { required: true, message: "姓名必填", trigger: "blur" }
          ],
          genealogySex: [
            { required: true, message: "性别必填", trigger: "blur" }
          ]
        }
      },
      data: {
        param: {genealogyId:this.$route.params.id,},
        columns: [
          {
            type: "selection"
          },
          {
            title: "操作",
            key: "options",
            render: (h, params) => {
              const edit = h("Button", {
                props: {
                  type: "info",
                  shape: "circle",
                  icon: "md-hammer",
                  size: "small"
                },
                on: {
                  click: () => {
                    let $this = this;
                    this.$ajax({
                      method: "post",
                      url: $this.url.get,
                      data: {
                        id: params.row.id
                      }
                    }).then(function(res) {
                      $this.editshow = true;
                      res.data.data.genealogyId=$this.$route.params.id
                      $this.detail.data = res.data.data;
                    });
                    $this.editshow = false;
                  }
                }
              });
              const remove = h("Button", {
                props: {
                  type: "error",
                  shape: "circle",
                  icon: "md-trash",
                  size: "small"
                },
                on: {
                  click: () => {
                    let $this = this;
                    let array = [];
                    array.push(params.row.id);
                    this.$ajax({
                      method: "post",
                      url: $this.url.del,
                      data: {
                        ids: array
                      }
                    }).then(function(res) {
                      $this.$refs.table.searchData();
                      $this.$refs.topo.loadData();
                      $this.successModal("删除成功");
                    });
                  }
                }
              });
              const blank = h("span", { class: "blank ivu-icon" });
              return h("div", [edit, blank, remove]);
            }
          },
          {
            title: "姓名",
            key: "genealogyName",
            sortable: "custom"
          },
          {
            title: "性别",
            key: "genealogySex",
            sortable: "custom"
          },
          {
            title: "生日",
            key: "genealogyBirthday",
            render: function(h, params) {
              if (params.row.genealogyBirthday) {
                return h(
                  "div",
                  new Date(params.row.genealogyBirthday).Format(
                    "yyyy-MM-dd hh:mm:ss"
                  )
                );
              }
            }
          },
          {
            title: "卒日",
            key: "genealogyDeadtime",
            render: function(h, params) {
              if (params.row.genealogyDeadtime) {
                return h(
                  "div",
                  new Date(params.row.genealogyDeadtime).Format(
                    "yyyy-MM-dd hh:mm:ss"
                  )
                );
              }
            }
          }
        ]
      },
      selectData: {
        param: {},
        columns: [
          {
            title: "姓名",
            key: "genealogyName",
            sortable: "custom"
          },
          {
            title: "性别",
            key: "genealogySex",
            sortable: "custom"
          },
          {
            title: "生日",
            key: "genealogyBirthday",
            render: function(h, params) {
              if (params.row.genealogyBirthday) {
                return h(
                  "div",
                  new Date(params.row.genealogyBirthday).Format(
                    "yyyy-MM-dd hh:mm:ss"
                  )
                );
              }
            }
          },
          {
            title: "卒日",
            key: "genealogyDeadtime",
            render: function(h, params) {
              if (params.row.genealogyDeadtime) {
                return h(
                  "div",
                  new Date(params.row.genealogyDeadtime).Format(
                    "yyyy-MM-dd hh:mm:ss"
                  )
                );
              }
            }
          }
        ]
      },
      
    };
  },
  computed: {
    functions:function(){
      let $this = this;
      return {
          onRowDblclick:function(val){
            let config = {
              title:"确认",
              content:"确认是  "+val.genealogyName+"  吗",
              onOk:function(){
                $this.personShow = false;
                if($this.PorS == "P"){
                  $this.detail.data.parentId = val.id
                  $this.add.data.parentId = val.id
                }else if($this.PorS == "S"){
                  $this.detail.data.spouseId = val.id
                  $this.add.data.spouseId = val.id
                }
              }
            }
            $this.$Modal.confirm(config)
          }
        }
      },
    detailDate: function() {
      let data = this.detail.data;
      for (let key in data) {
        if (key == "genealogyBirthday" || key == "genealogyDeadtime") {
          if(data[key]){
            data[key] = new Date(data[key]);
          }
        }
      }
      return data;
    }
  },
  methods: {
    search: function() {
      this.data.param = this.searchData;
      this.$refs.table.searchData(this.data.param);
    },
    search2: function() {
      this.selectData.param = this.searchData2;
      this.$refs.table2.searchData(this.selectData.param);
    },
    dropdownFunction: function(name) {
      if (name == "add") {
        let $this = this;
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
          $this.add.data = {genealogyId:$this.$route.params.id}
        }, 1);
      }
    },
    editok: function() {
      let $this = this;
      if (this.validateForm(this.$refs.editModal)) {
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
        return;
      }
      this.$ajax({
        method: "post",
        url: this.url.edit,
        data: $this.detailDate
      }).then(function(res) {
        $this.$refs.table.searchData();
        $this.$refs.topo.loadData();
        $this.detail.data = {};
        $this.successModal("编辑成功");
      });
    },
    addok: function() {
      let $this = this;
      if (this.validateForm(this.$refs.addModal)) {
        $this.addshow = false;
        setTimeout(function() {
          $this.addshow = true;
        }, 1);
        return;
      }
      this.$ajax({
        method: "post",
        url: this.url.add,
        data: $this.add.data
      }).then(function(res) {
        $this.$refs.table.searchData();
        $this.$refs.topo.loadData();
        $this.add.data = {};
        $this.successModal("新增成功");
      });
      this.addshow = false;
    },
    personOk: function() {},
    openPersonShow: function(ps) {
      this.PorS = ps;
      this.personShow = true;
      this.searchData2 = {
        genealogyName: ""
      }
      this.search2();
    },
    validateForm: function(form) {
      let flag = false;
      form.validate(valid => {
        if (!valid) {
          flag = true;
        }
      });
      return flag;
    },
  },
  
};
</script>
<style lang="less" scoped>
.tabs {
  background-color: #fff;
}
#tp{
  width: 100%;
  height: 100%;
}
</style>
