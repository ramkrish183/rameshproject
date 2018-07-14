define([
  'charts/theme',
  'charts/theme-dark',
  'highcharts-more'
], function (theme) {
  'use strict';
  Highcharts.setOptions(theme);
  return function (el, options) {
    var cfg = {
      chart: {
        //renderTo: el,
        type: 'boxplot'
      }
    };
    return new Highcharts.Chart($.extend(true, {}, cfg, options));
  };
});