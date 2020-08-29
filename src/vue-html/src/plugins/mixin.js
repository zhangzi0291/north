const globalMethods = {
  methods: {
    successModal(message) {
      this.$Message.success(message);
    },
    warningModal(message) {
      this.$Message.warning(message);
    },
    errorModal(message) {
      this.$Message.error(message);
    },
    infoModal(message) {
      this.$Message.info(message);
    },
    toHump(name) {
      return name.replace(/\_(\w)/g, function(all, letter) {
        return letter.toUpperCase();
      });
    },
    // 驼峰转换下划线
    toLine(name) {
      return name.replace(/([A-Z])/g, "_$1").toLowerCase();
    },
    handleChange (newTargetKeys, direction, moveKeys) {
      if(direction == 'right'){
        this.exportTargetKeys = this.exportTargetKeys.concat(moveKeys)
      }
      if(direction == 'left'){
        for (let i = 0; i < this.exportTargetKeys.length; i++) {
          const oldElement = this.exportTargetKeys[i];
          for (let j = 0; j < moveKeys.length; j++) {
            const removeElement = moveKeys[0];
            if(oldElement == removeElement){
              this.exportTargetKeys.splice(i, 1);
            }
          }
        }
      }
    }
  }
};

export { globalMethods };
