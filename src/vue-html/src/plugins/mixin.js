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
    }
  }
};

export { globalMethods };
