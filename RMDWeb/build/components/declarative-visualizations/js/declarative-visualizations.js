define([
    'declarative-visualizations/bar',
    'declarative-visualizations/donut',
    'declarative-visualizations/gauge',
    'declarative-visualizations/spiderweb'
  ], function(bar, donut, gauge, spiderweb) {
    'use strict';
    return {
      bar: bar,
      donut: donut,
      gauge: gauge,
      spiderweb: spiderweb
    };
  }
);