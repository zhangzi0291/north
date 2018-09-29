<style scoped>
.carousel {
  width: 50%;
  margin: 0 auto;
}

.carousel-item {
  text-align: center;
}

.carousel-img {
  width: 100%;
  height: 300px;
  line-height: 300px;
  color: #fff;
  font-size: 20px;
  background: #506b9e;
}
</style>
<template>
  <div>
    <Carousel 
      v-model="carouselValue" 
      :arrow="'hover'" 
      loop 
      class='carousel'>
      <CarouselItem class="carousel-item">
        <img 
          class="carousel-img" 
          src="/static/images/logo.jpg" 
          key="max-logo" >
      </CarouselItem>
      <CarouselItem>
        <div class="arousel-img">2</div>
      </CarouselItem>
      <CarouselItem>
        <div class="arousel-img">3</div>
      </CarouselItem>
      <CarouselItem>
        <div class="arousel-img">4</div>
      </CarouselItem>
    </Carousel>
  </div>
</template>
<script>
export default {
  data() {
    return {
      carouselValue: 0
    };
  }
};
</script>
