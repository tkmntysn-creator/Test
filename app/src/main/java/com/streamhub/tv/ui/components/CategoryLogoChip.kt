package com.streamhub.tv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.streamhub.tv.data.model.Channel
import com.streamhub.tv.data.model.ChannelCategory

/**
 * A single "story bubble" style category entry: a circular logo (taken from the first
 * channel in that category) with the category name underneath. Tapping it opens every
 * channel in that category (see CategoryDetailScreen).
 */
@Composable
fun CategoryLogoChip(
    category: ChannelCategory,
    representativeLogo: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(76.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = representativeLogo,
                contentDescription = category.displayName,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            category.displayName,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

/**
 * Horizontal row of [CategoryLogoChip]s, one per category that currently has channels.
 * The logo shown for each bubble is simply the first channel's logo in that category
 * (e.g. the first Sports channel's logo represents the whole "Sports" bubble).
 */
@Composable
fun CategoryLogoRow(
    categorized: Map<ChannelCategory, List<Channel>>,
    onCategoryClick: (ChannelCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
    ) {
        items(ChannelCategory.orderedCategories) { category ->
            val channelsInCategory = categorized[category].orEmpty()
            if (channelsInCategory.isNotEmpty()) {
                CategoryLogoChip(
                    category = category,
                    representativeLogo = channelsInCategory.first().logo,
                    onClick = { onCategoryClick(category) }
                )
            }
        }
    }
}
