<style scoped lang="less">

</style>
<template>
    <div>
        <div class="page-title">
            <Breadcrumb>
                <BreadcrumbItem to="/home">首页</BreadcrumbItem>
                <BreadcrumbItem to="/sys/user">用户管理</BreadcrumbItem>
            </Breadcrumb>
        </div>
        <div class="page-content">
            <div>
                <Form inline :model="searchData">
                    <FormItem label-for='search'>
                        <Input v-model="searchData.username" class="input-search" placeholder="用户名" clearable></Input>
                    </FormItem>
                    <FormItem label-for='search2'>
                        <Input v-model="searchData.name" class="input-search" placeholder="名称" clearable></Input>
                    </FormItem>
                    <FormItem label-for='search4'>
                        <Input v-model="searchData.mobile" class="input-search" placeholder="手机号" clearable></Input>
                    </FormItem>
                    <FormItem>
                        <Button type="primary" @click='search'>查询</Button>
                        <Dropdown trigger="click" @on-click='dropdownFunction'>
                            <Button type="primary">
                                其他功能
                                <Icon type="arrow-down-b"></Icon>
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
        </div>
        <div class="page-content">
            <t-table ref='table' :url='url.list' :param='data.param' :columns='data.columns'></t-table>
        </div>
        <m-modal ref='editModal' :title="'编辑'" :show='editshow' :data='detail.data' :columns='detail.columns' :ok='editok' :rule='detail.rule'></m-modal>
        <m-modal ref='addModal' :title="'新增'" :show='addshow' :data='add.data' :columns='add.columns' :ok='addok' :rule='add.rule'></m-modal>
    </div>
</template>
<script>
export default {
    name: "sysuser",
    data() {
        return {
            url: {
                list: "/sys/user/list",
                add: '/sys/user/add',
                get: '/sys/user/get',
                edit: '/sys/user/edit',
                del: '/sys/user/del'
            },
            detail: {
                data: {},
                columns: [
                    { key: "name", value: "名称", rule: [{ required: true, message: "名称必填", trigger: 'blur' }], },
                    { key: "username", value: "用户名" },
                    { key: "password", value: "密码", type: 'password' },
                    { key: "mobile", value: "手机号" },
                    { key: "email", value: "电子邮件" },
                    { key: "expiredTime", value: "过期时间", type: 'date' },
                    { key: "roleId", value: "角色", type: 'select', ajax: { url: 'sys/role/selectOptions' } },
                    { key: "status", value: "状态", type: 'select', child: [{ name: '可用', value: '1' }, { name: '禁用', value: '2' }] },
                ],
                rule: {
                    "name": [{ required: true, message: "名称必填", trigger: 'blur' }],
                    "username": [{ required: true, message: "用户名必填", trigger: 'blur' }],
                }
            },
            add: {
                data: {},
                columns: [
                    { key: "name", value: "名称" },
                    { key: "username", value: "用户名" },
                    { key: "password", value: "密码", type: 'password' },
                    { key: "mobile", value: "手机号" },
                    { key: "email", value: "电子邮件", },
                    { key: "expiredTime", value: "过期时间", type: 'date' },
                    { key: "roleId", value: "角色", type: 'select', ajax: { url: 'sys/role/selectOptions' } },
                    { key: "status", value: "状态", type: 'select', child: [{ name: '可用', value: '1' }, { name: '禁用', value: '2' }] },
                ],
                rule: {
                    "name": [{ required: true, message: "名称必填", trigger: 'blur' }],
                    "username": [{ required: true, message: "用户名必填", trigger: 'blur' }],
                    "password": [{ required: true, message: "密码必填", trigger: 'blur' }]
                }
            },
            searchData: {
                username: '',
                name: '',
                mobile: '',
            },
            editshow: false,
            addshow: false,
            data: {
                param: {},
                columns: [
                    {
                        type: 'selection'
                    },
                    {
                        title: '操作',
                        key: 'options',
                        render: (h, params) => {
                            const edit = h('Button', {
                                props: { type: 'ghost', shape: 'circle', icon: 'edit', size: 'small' }, on: {
                                    click: () => {
                                        let $this = this;
                                        this.$ajax({
                                            method: 'post',
                                            url: $this.url.get,
                                            data: {
                                                id: params.row.id
                                            }
                                        }).then(function(res) {
                                            $this.editshow = true;
                                            $this.detail.data = res.data.data
                                        })
                                        $this.editshow = false;
                                    }
                                }
                            });
                            // const edit = h('Button', { props: { type: 'ghost', shape: 'circle', icon: 'edit', size: 'small' } });
                            const remove = h('Button', {
                                props: { type: 'ghost', shape: 'circle', icon: 'trash-a', size: 'small' }, on: {
                                    click: () => {
                                        let $this = this;
                                        let array = [];
                                        array.push(params.row.id)
                                        this.$ajax({
                                            method: 'post',
                                            url: $this.url.del,
                                            data: {
                                                ids: array
                                            }
                                        }).then(function(res) {
                                            $this.$refs.table.searchData()
                                            $this.successModal("删除成功")
                                        })
                                    }
                                }
                            });
                            const blank = h('span', { class: 'blank ivu-icon' })
                            return h('div', [edit, blank, remove])
                        }
                    },
                    {
                        title: '名字',
                        key: 'name',
                        sortable: 'custom'
                    },
                    {
                        title: '用户名',
                        key: 'username',
                        sortable: 'custom'
                    },
                    {
                        title: '手机号',
                        key: 'mobile'
                    },
                    {
                        title: '电子邮件',
                        key: 'email'
                    },
                    {
                        title: '过期时间',
                        key: 'expiredTime',
                        render: function(h, params) {
                            if (params.row.expiredTime) {
                                return h("div", new Date(params.row.expiredTime).Format("yyyy-MM-dd hh:mm:ss"))
                            }
                        }
                    },
                    {
                        title: '创建时间',
                        key: 'createTime',
                        render: function(h, params) {
                            return h("div", new Date(params.row.createTime).Format("yyyy-MM-dd hh:mm:ss"))
                        }
                    },
                    {
                        title: '状态',
                        key: 'status',
                        sortable: 'custom'
                    },
                ],
            },
        }
    },
    methods: {
        search: function() {
            this.data.param = this.searchData
            this.$refs.table.searchData()
        },
        dropdownFunction: function(name) {
            if (name == 'add') {
                let $this = this;
                $this.addshow = false;
                setTimeout(function() {
                    $this.addshow = true;
                }, 1);
            }
        },
        editok: function() {
            let $this = this;
            if (this.$refs.editModal.validateForm()) {
                $this.addshow = false
                setTimeout(function() {
                    $this.addshow = true;
                }, 1);
                return
            }
            this.$ajax({
                method: 'post',
                url: this.url.edit,
                data: this.$refs.editModal.getParams()
            }).then(function(res) {
                $this.$refs.table.searchData()
                $this.detail.data = {}
                $this.successModal("编辑成功")
            })
        },
        addok: function() {
            let $this = this;
            this.$refs.addModal.getParams()
            if (this.$refs.addModal.validateForm()) {
                $this.addshow = false
                setTimeout(function() {
                    $this.addshow = true;
                }, 1);
                return
            }
            this.$ajax({
                method: 'post',
                url: this.url.add,
                data: this.$refs.addModal.getParams()
            }).then(function(res) {
                $this.$refs.table.searchData()
                $this.add.data = {}
                $this.successModal("新增成功")
            })
            this.addshow = false;
        }

    },
    mounted() {
        this.data.param = this.searchData
    }

}
</script>
