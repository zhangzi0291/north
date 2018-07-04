<template>
    <Modal v-model="isshow" :title="title" width="380" @on-ok='ok' @on-cancel='cancel' transfer	>
        <Form ref="form" :model="data" :inline='inline' :rules='rule'>
            <template v-for="item in columns">
                <FormItem  :label="item.value+'：'" :key="item.id" :v-model="data[item.key]" :prop="item.key">
                    <Select v-model="data[item.key]" :readonly='item.readonly' v-if="item.type=='select'">
                        <Option v-for="op in item.child" :value="op.value" :key="op.value">{{ op.name }}</Option>
                    </Select>
                    <RadioGroup v-model="data[item.key]" v-else-if="item.type=='radio'">
                        <Radio v-for="op in item.child" :value="op.value" :key="op.value" :label="op.name"></Radio>
                    </RadioGroup>
                    <template v-else-if="item.type=='date'">
                        <br>
                        <DatePicker v-model="data[item.key]" :readonly='item.readonly' type="datetime" style="width: 100%"></DatePicker>
                    </template>
                    <template v-else-if="item.type=='daterange'">
                        <br>
                        <DatePicker v-model="data[item.key]" :readonly='item.readonly' type="datetimerange" style="width: 100%" split-panels></DatePicker>
                    </template>
                    <Input v-model="data[item.key]" :readonly='item.readonly' type="password" v-else-if="item.type=='password'"></Input>
                    <Input v-model="data[item.key]" :readonly='item.readonly' v-else></Input>
                </FormItem>
            </template>
        </Form>
    </Modal>
</template>
<script>
export default {
    // name: 'mModal',
    data() {
        return {
            isshow: this.show,
        }
    },
    computed: {
        params: function() {
            let array = [];
            for (x in this.data) {
                array.push(x, this.data.x);
            }
            return array
        },
        inline: function() {
            return Object.keys(this.columns).length > 8 ? true : false
        }

    },
    watch: {
        show(val) {
            this.isshow = val
        },
        data(val) {
            let $this = this;
            this.columns.forEach(function(val, index, err) {
                // if (val.type == 'password') {
                //     $this.data[val.key] = undefined
                // }
                if ( (val.type == 'date' || val.type == 'daterange') &&  $this.data[val.key]) {
                    console.log($this.data[val.key])
                    $this.data[val.key] = new Date($this.data[val.key])
                }
            })
        }
    },
    mounted() {
        let $this = this;
        this.columns.forEach(function(val, index, err) {
            if (val.type == 'password') {
                // console.log($this.data)
                // $this.data[val.key] = undefined
            }
            if (val.ajax) {
                $this.$ajax({
                    method: 'post',
                    url: val.ajax.url,
                    data: val.ajax.data
                }).then(function(res) {
                    val.child = res.data.data
                })
            }
        })
    },
    props: {
        title: {
            type: String
        },
        rule: {
            type: Object
        },
        data: {
            type: Object
        },
        columns: {
            type: Array
        },
        show: {
            type: Boolean
        },
        ok: {
            type: Function,
            default: function() {

            }
        },
        cancel: {
            type: Function,
            default: function() {

            }
        },
    },
    methods: {
        getParams: function() {
            for (var i = 0; i < this.columns.length; i++) {
                var element = this.columns[i];
                let key = element.key;
                let type = element.type;
                if ((type == 'date' || type == 'daterange')&&this.data[key]) {
                    this.data[key] = (this.data[key].getTime())
                }
            }
            console.log(this.data)
            return this.data
        },
        validateForm: function(){
            let flag = false
            this.$refs.form.validate((valid) => {
                if (!valid) {
                    flag = true;
                }
            })
            return flag
        }
    }
}
</script>
