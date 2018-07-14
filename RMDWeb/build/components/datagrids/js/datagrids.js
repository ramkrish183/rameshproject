/*jshint quotmark:false*/

define([
  'jquery',
  'responsive-emitter',
  // custom floater behavior
  'datagrids/datagrids-floater',
  'datatables',
  'col-reorder-amd',
  // custom paging behavior
  'datagrids/datagrids-paging'
],

function($, ResponsiveEmitter, addFloater) {
  'use strict';

  /* IIDS BASIC DATA GRID PUBLIC CLASS DEFINITION
  * ============================================ */

  var IIDSBasicDataGrid = function (element, options) {
    this.init('iidsBasicDataGrid', element, options);
  };

  IIDSBasicDataGrid.prototype = {

    constructor: IIDSBasicDataGrid,

    init: function(type, element, options) {
      this.type = type;
      this.$element = $(element);
      this.name = this.$element.data('table-name');
      this.settings = this.getOptions(options);

      if (this.settings.useFloater) {
        addFloater(this.settings);
      }

      this.createTable(options);
    },

    createTable: function(options) {
      // Init the datatable
      this.$table = this.$element.dataTable(this.settings);

      // Row Selection
      this.$table.on('click', 'tbody tr', $.proxy(this.clicked, this));

      // Search filter
      $('input:text[data-filter-table="' + this.name + '"]').on('keyup', $.proxy(this.searched, this));

      // Responsive
      if (this.settings.isResponsive) {
        this.addResponsiveListeners();
      }
    },

    getOptions: function(options) {
      options = $.extend({}, $.fn[this.type].defaults, options, this.$element.data());
      return options;
    },

    // Handle row clicks. Whenever the user
    // clicks a row dispatch a `selected` event
    // and pass along the row that was clicked and
    // a collection of all selected rows.
    clicked: function(e) {
      var self = this;
      var $row = $(e.currentTarget);
      // If TableTools is being used then get out of the way
      // since it provides its own click handlers
      if (!$.fn.DataTable.TableTools) {
        $row.toggleClass('row_selected');
      }

      // Make the dispatch async so we can be sure that plugins
      // like TableTools have had a chance to run first
      setTimeout(function() {
        var $rows = self.$table.find('.row_selected');
        self.$element.trigger('selected', [$row, $rows]);
      }, 0);
    },

    searched: function(e) {
      this.$table.fnFilter($(e.currentTarget).val());
    },

    addResponsiveListeners: function() {
      // These events rely on the responsive-emitter plugin.
      // This API could be made more robust by allowing the user to pass in their
      // own breakpoints.

      // landscape tablet
      ResponsiveEmitter.register('(min-width: 768px) and (max-width: 979px)',
        $.proxy(this.respondLandscapeTablet, this)
      );

      // portrait tablet / landscape phone
      ResponsiveEmitter.register('(min-width: 481px) and (max-width: 767px)',
        $.proxy(this.respondPortraitTablet, this)
      );

      // portrait phone
      ResponsiveEmitter.register('(max-width: 480px)',
        $.proxy(this.respondPhone, this)
      );

      $(window).on('resize', $.proxy(this.resize, this));
    },

    respondLandscapeTablet: function() {
      if (this.settings.isResponsive && !this.settings.excludes.landscapeTablet) {
        $.each(this.$table.dataTable().fnSettings().aoColumns, $.proxy(function(i, col) {
          this.$table.dataTable().fnSetColumnVis(i, true);
        }, this));
        this.resize();
      }
    },

    respondPortraitTablet: function() {
      if (this.settings.isResponsive && !this.settings.excludes.portraitTablet) {
        $.each(this.$table.dataTable().fnSettings().aoColumns, $.proxy(function(i, col) {
          if (!$(col.nTh).hasClass('essential') && !$(col.nTh).hasClass('optional')) {
            this.$table.dataTable().fnSetColumnVis(i, false);
          } else {
            this.$table.dataTable().fnSetColumnVis(i, true);
          }
        }, this));
        this.resize();
      }
    },

    respondPhone: function() {
      if (this.settings.isResponsive && !this.settings.excludes.phone) {
        $.each(this.$table.dataTable().fnSettings().aoColumns, $.proxy(function(i, col) {
          if (!$(col.nTh).hasClass('essential')) {
            this.$table.dataTable().fnSetColumnVis(i, false);
          }
        }, this));
        this.resize();
      }
    },

    resize: function() {
      this.$table.width(this.$table.parent().innerWidth());
    }
  };


 /* IIDS BASIC DATA GRID PLUGIN DEFINITION
  * ========================= */

  $.fn.iidsBasicDataGrid = function (option) {
    return this.each(function () {
      var $this = $(this),
          data = $this.data('iidsBasicDataGrid'),
          options = typeof option === 'object' && option;
      if (!data) $this.data('iidsBasicDataGrid', (data = new IIDSBasicDataGrid(this, options)));
      if (typeof option === 'string') data[option]();
    });
  };

  $.fn.iidsBasicDataGrid.Constructor = IIDSBasicDataGrid;

  $.fn.iidsBasicDataGrid.defaults = {
    'useFloater': true,
    'isResponsive': false,
    'excludes': {
      'landscapeTablet': false,
      'portraitTablet': false,
      'phone': false
    },
    'bScrollCollapse': true,
    'oLanguage': {
      'sInfo': '<strong>_START_</strong> - <strong>_END_</strong> of <strong>_TOTAL_</strong>',
      'sInfoEmpty': '<strong>0</strong> - <strong>0</strong> of <strong>0</strong>'
    },
    // bootstrap pagination from http://datatables.net/media/blog/bootstrap_2/DT_bootstrap.js
    'sDom': "TRrt<'table-controls'<'pull-left'l><'pull-right'ip>>",
    'sPaginationType': 'bootstrap'
  };
});
