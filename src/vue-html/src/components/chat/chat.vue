<template>
    <div>
        <Row type="flex" justify="center" align="middle" :style="{height: msgBoxHeight}">
            <ul>
                <li v-for="item in context">
                    {{item.auth}}--{{item.msg}}
                </li>
            </ul>
        </Row>
        <Row type="flex" justify="center" align="middle">
            <Input v-model="msg" class="sendInput sendBox" size="large"></Input><Button @click='send'  class="sendButton sendBox" type="primary" size="large">发送</Button>
        </Row>
        <Row type="flex" justify="center" align="middle" >
            <!--<Col span="8">
            <i-button @click='send'>send</i-button>
            </Col>
            <Col span="8">
            <i-button @click='restart'>restart</i-button>
            </Col>
            <Col span="8">
            <i-button @click='close'>close</i-button>
            </Col>-->
        </Row>
    </div>
</template>
<script>
// let ws = new WebSocket("ws://localhost:8000/springboot/wchat");
export default {
    data() {
        return {
            msg: '123',
            msgBoxHeight:"600px",
            context: []
        }
    },
    mounted() {
        const $this = this;

        const inputHeight = 46;
        this.msgBoxHeight = window.document.documentElement.clientHeight-inputHeight+"px"
        window.onresize = () =>{
             this.msgBoxHeight = window.document.documentElement.clientHeight-inputHeight+"px"
        }
        // window.onbeforeunload = function(evt) {
        //     $this.$Modal.error({
        //         title: "网络错误",
        //         content: "连接建立失败"
        //     });
        // }
        // ws.onopen = function(evt) {
        //     console.log(evt)
        //     console.log("Connection open ...");
        // };

        // ws.onmessage = function(evt) {
        //     console.log("Received Message: " + evt.data);
        //     $this.context.push(JSON.parse(evt.data))
        // };
        // ws.onerror = function(evt) {
        //     console.log("Connection error");
        //     $this.$Modal.error({
        //         title: "网络错误",
        //         content: "连接服务器失败"
        //     });
        // };
        // ws.onclose = function(evt) {
        //     console.log("Connection closed.");
        // };
    },
    methods: {
        // send: function() {
        //     ws.send(this.msg);
        // },
        // restart: function() {
        //     console.log("restart")
        // },
        // close: function() {
        //     ws.close();
        // }
    }
}
</script>
<style scoped>
.sendBox{
    margin: 5px;
}
.sendInput{
    width: calc(100% - 100px );
}
.sendButton{
    width: 80px;
}
</style>
