<style scoped lang="less">
    .right{
        flex: 1;
        text-align: right;
    }
    .search-box{
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .button-box{
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }
    .button-box>button{
        margin: 5px;
    }
    /deep/ .tree-render .ivu-tree-title {
        width: 100%;
    }
</style>
<template>
    <div>
        <div class="page-title">
            <!-- 标题面包屑 -->
            <Breadcrumb>
                <BreadcrumbItem to="/home/index">首页</BreadcrumbItem>
                <BreadcrumbItem to="/sys/log/list">{{title}}</BreadcrumbItem>
            </Breadcrumb>
        </div>
        <Form inline :model="searchData">
            <div class="page-content">
                <!-- 表格条件查询 -->
                <div class="search-box">
                    <div class="button-box right">
                        <FormItem >
                            <!-- 其他功能下拉框 -->
                            <Dropdown trigger='click' @on-click="dropdownFunction" >
                                <Button type="success">
                                    功能
                                    <Icon type="md-arrow-dropdown"></Icon>
                                </Button>
                                <DropdownMenu slot="list">
                                    <DropdownItem name="openAdd">
                                        <div style="text-align:left">
                                            <Icon type="md-add-circle" size="18" />
                                            <span>新增</span>
                                        </div>
                                    </DropdownItem>
                                    <DropdownItem divided name="openImport">
                                        <div style="text-align:left">
                                            <Icon type="md-cloud-upload" size="18" />
                                            <span >导入Excel数据</span>
                                        </div>
                                    </DropdownItem>
                                    <DropdownItem name="openExport">
                                        <div style="text-align:left">
                                            <Icon type="md-cloud-download" size="18" />
                                            <span >导出Excel数据</span>
                                        </div>
                                    </DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                        </FormItem>
                        <FormItem >
                            <Button type="primary" @click="search">搜索</Button>
                        </FormItem>
                        <FormItem >
                            <Button @click="cancel">取消</Button>
                        </FormItem>
                    </div>
                </div>
            </div>
        </Form>
        <div class="page-content">
            <!-- 表格 -->
            <!-- <t-table ref="table" :url="url.list" :param="data.param" :columns="data.columns" /> -->
            <Tree
                :data="dictData"
                children-key="children"
                :render="render"
                show-checkbox
                class="tree-render"
            ></Tree>    
        </div>
        <!-- 详情模态窗 -->
        <d-detail ref="detail" :title="title" :getUrl="url.get" :label="exportLabel" :keyName="exportKey" />
        <!-- 新增和编辑模态窗 -->
        <form-modal ref="formModal" :title="title" :getUrl="url.get" :addUrl="url.add" :editUrl="url.edit" :okCallback="okCallback" />
        <!-- 导入模态窗 -->
        <i-import ref="import" :getImportExcelUrl="url.importExcel" :getTemplateUrl="url.template" />
        <!-- 导出模态窗 -->
        <e-export ref="export" :getExportExcelUrl="url.exportExcel" :exportLabel="exportLabel" :exportKey="exportKey" />

    </div>
</template>
<script>
    import formModal from "./formModal"
    export default {
        name: "sysLog",
        components: {
            formModal,
        },
        data() {
            return {
                title:"系统日志",
                //ajax调用的url汇总
                url: {
                    list: "/sys/dict/list",
                    all: "/sys/dict/all",
                    add: "/sys/dict/add",
                    get: "/sys/dict/get",
                    edit: "/sys/dict/edit",
                    del: "/sys/dict/del",
                    importExcel: baseURL+"/sys/dict/importExcel?userId=" + JSON.parse(localStorage.getItem("user")).id,
                    exportExcel: baseURL+"/sys/dict/exportExcel",
                    template: baseURL+"/sys/dict/exportTemplete",
                },
                dictData:[],
                //表格查询字段信息
                searchData: {
                },
                //导出中文名
                exportLabel: ["ID","字典名称","Key","Value",] ,
                //导出字段名
                exportKey:["id","dictName","dictKey","dictValue",],
                //表格内容
                render: (h, params) => {
                    const add = h("Button", {
                    props: {
                        type: "success",
                        shape: "circle",
                        icon: "md-add",
                        size: "small"
                    },
                    on: {
                        click: () => {
                            this.openAdd()
                        }
                    }
                    });

                    const edit = h("Button", {
                        props: {
                            type: "info",
                            shape: "circle",
                            icon: "md-hammer",
                            size: "small"
                        },
                        on: {
                            click: () => {
                                console.log(params.data.id)
                                this.openEdit(params.data.id)
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
                        this.$Modal.confirm({
                            title: "确定删除吗",
                            onOk: () => {
                            let array = [];
                            array.push(params.data.id);
                            this.$ajax({
                                method: "post",
                                url: $this.url.del,
                                data: {
                                    ids: array
                                }
                            }).then(function(res) {
                                $this.getAllDict();
                                $this.successModal("删除成功");
                            });
                            }
                        });
                        }
                    }
                    });
                    const blank = h("span", { class: "blank ivu-icon" });
                    let button;

                    if(!params.data.id){
                    button = [params.data.dictName, h("div", [add, blank, remove])]
                    }else{
                    button = [params.data.dictName, h("div", [edit, blank, remove])]
                    }
                    return h(
                    "div",
                    {
                        style: {
                        display: "flex",
                        "justify-content": "space-between",
                        width: "calc(100% - 40px)"
                        }
                    },
                    button
                    );
                }
            };
        },

        methods: {
            //执行表格查询
            search: function() {
                this.data.param = this.searchData;
                // this.$refs.table.searchData();
            },
            //下拉框name填函数名，直接调用函数
            dropdownFunction: function(name) {
                this[name]()
            },
            //打开导入模态窗
            openImport(){
                this.$refs.import.changeShow()
            },
            //打开导出模态窗
            openExport(){
                this.$refs.export.changeShow()
            },
            //打开新增模态窗
            openAdd(){
                this.$refs.formModal.changeShow()
            },
            //打开编辑模态窗
            openEdit(id){
                this.$refs.formModal.changeShow(id)
            },
            //打开详情模态窗
            openDetail(id){
                this.$refs.detail.changeShow(id)
            },
            //取消筛选值
            cancel(){
                this.searchData={}
                this.data.param = this.searchData;
            },
            okCallback(){
                this.getAllDict()
            },
            getAllDict: function() {
                let $this = this;
                this.$ajax({
                    method: "post",
                    url: this.url.all
                }).then(function(res) {
                    let rows = res.data.rows;
                    let data = []
                    let map = {};
                    rows.map(item =>{
                        map[item.dictName] = {
                            dictName:item.dictName,
                            children:[]
                        }
                    })
                    rows.map(item =>{
                        map[item.dictName].children.push(item)
                    })
                    for(let key in map) {
                        let obj = map[key]
                        data.push(obj)
                    }
                    $this.dictData = data;
                    // $this.detail.data = {};
                });
            },
        },
        created(){
            this.getAllDict()
        },

    };
</script>
